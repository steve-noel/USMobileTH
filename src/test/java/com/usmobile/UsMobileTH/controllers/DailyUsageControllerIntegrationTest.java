package com.usmobile.UsMobileTH.controllers;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomCycle;
import static com.usmobile.UsMobileTH.utils.TestUtils.randomDailyUsage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

import com.usmobile.UsMobileTH.dtos.daily_usage.CurrentCycleDailyUsageResponse;
import com.usmobile.UsMobileTH.dtos.daily_usage.DailyUsageDTO;
import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.models.DailyUsage;
import com.usmobile.UsMobileTH.repositories.CycleRepository;
import com.usmobile.UsMobileTH.repositories.DailyUsageRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DailyUsageControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private DailyUsageRepository dailyUsageRepository;

    private String userId;
    private String mdn;
    private List<Cycle> existingCycles;
    private List<DailyUsage> dailyUsages;
    private ResponseEntity<CurrentCycleDailyUsageResponse> returnedCurrentCycleUsage;

    @BeforeEach
    public void before() {
        cycleRepository.deleteAll();
        dailyUsageRepository.deleteAll();
    }

    @Test
    public void getCurrentDailyUsage() {
        givenUserIdAndMdn();
        setup();
        whenGetCurrentCycleUsage();
        thenCurrentCycleUsageReturned();
    }

    public void setup() {
        final Instant now = Instant.now();
        final Consumer<DailyUsage> currentCycleUsage = (du) -> {
            du.setMdn(mdn);
            du.setUserId(userId);
        };
        final Consumer<Cycle> currentCycle = (cycle) -> {
            cycle.setMdn(mdn);
            cycle.setUserId(userId);
            cycle.setStartDate(now.minus(Duration.ofDays(10)));
            cycle.setEndDate(now.plus(Duration.ofDays(10)));
        };
        final Consumer<Cycle> inValidCycle = (cycle) -> {
            cycle.setMdn(mdn);
            cycle.setUserId(userId);
            cycle.setStartDate(now.minus(Duration.ofDays(10)));
        };
        existingCycles = List.of(randomCycle(currentCycle),randomCycle(inValidCycle));
        existingCycles = cycleRepository.saveAll(existingCycles);

        dailyUsages =
                List.of(randomDailyUsage(currentCycleUsage), randomDailyUsage(currentCycleUsage),randomDailyUsage());

        for (int i =0; i < dailyUsages.size();i++) {
            DailyUsage du = dailyUsages.get(i);
            du.setUsageDate(Instant.now().minus(Duration.ofDays(i)));
        }

        dailyUsages = dailyUsageRepository.saveAll(dailyUsages);
        dailyUsages.forEach(du -> du.setUsageDate(du.getUsageDate().truncatedTo(ChronoUnit.SECONDS)));
    }

    public void givenUserIdAndMdn(){
        userId = RandomStringUtils.randomAlphanumeric(10);
        mdn = RandomStringUtils.randomNumeric(10);
    }

    private void whenGetCurrentCycleUsage() {
        final String url = String.format("/daily_usage/current_cycle_usage/user/%s/mdn/%s", userId, mdn);
        returnedCurrentCycleUsage = testRestTemplate
                .exchange(url, HttpMethod.GET, null, CurrentCycleDailyUsageResponse.class);
    }

    private DailyUsageDTO toDailyUsageDto(final DailyUsage dailyUsage) {
        return DailyUsageDTO.builder()
                .date(dailyUsage.getUsageDate())
                .dailyUsage(dailyUsage.getUsedInMb()).build();
    }

    private void thenCurrentCycleUsageReturned() {
        assertNotNull(returnedCurrentCycleUsage);
        assertTrue(returnedCurrentCycleUsage.getStatusCode().is2xxSuccessful());
        assertNotNull(returnedCurrentCycleUsage.getBody());

        final CurrentCycleDailyUsageResponse currentCycleUsageBody = returnedCurrentCycleUsage.getBody();
        currentCycleUsageBody.getDailyUsage().forEach(du -> du.setDate(du.getDate().truncatedTo(ChronoUnit.SECONDS)));
        List<DailyUsageDTO> expectedDailyUsage = List.of(toDailyUsageDto(dailyUsages.get(0)),toDailyUsageDto(dailyUsages.get(1)));
        assertThat(currentCycleUsageBody.getDailyUsage(), is(Matchers.contains(allOf(
                        hasProperty("dailyUsage",is(expectedDailyUsage.get(0).getDailyUsage())),
                        hasProperty("date",is(expectedDailyUsage.get(0).getDate()))
                ),
                allOf(
                        hasProperty("dailyUsage",is(expectedDailyUsage.get(1).getDailyUsage())),
                        hasProperty("date",is(expectedDailyUsage.get(1).getDate()))

                )
        )));

    }
}
