package com.usmobile.UsMobileTH.controllers;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomCycle;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

import com.usmobile.UsMobileTH.TestContainersConfiguration;
import com.usmobile.UsMobileTH.dtos.cycle.CycleHistoryDTO;
import com.usmobile.UsMobileTH.dtos.cycle.CycleHistoryResponse;
import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.repositories.CycleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Import(TestContainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CycleControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CycleRepository repository;

    private String mdn;
    private String userId;
    private List<Cycle> existingCycles;
    private ResponseEntity<CycleHistoryResponse> returnedCycleHistory;

    @BeforeEach
    public void before() {
        repository.deleteAll();
    }

    @Test
    public void shouldGetCycleHistory(){
        givenUserIdAndMdn();
        setup();
        whenGetCycleHistory();
        thenCorrectCyclesAreReturned();
    }

    public void givenUserIdAndMdn(){
        userId = RandomStringUtils.randomAlphanumeric(10);
        mdn = RandomStringUtils.randomNumeric(10);
    }

    public void setup() {
        final Consumer<Cycle> validCycle = (cycle) -> {
            cycle.setMdn(mdn);
            cycle.setUserId(userId);
            cycle.setStartDate(Instant.now().minus(Duration.ofDays(10)));
            cycle.setEndDate(Instant.now().plus(Duration.ofDays(10)));
        };
        final Consumer<Cycle> inValidCycle = (cycle) -> {
            cycle.setMdn(mdn);
            cycle.setStartDate(Instant.now().minus(Duration.ofDays(10)));
        };
        existingCycles = List.of(randomCycle(validCycle),randomCycle(inValidCycle),randomCycle(validCycle));
        existingCycles = repository.saveAll(existingCycles);
        existingCycles.forEach(ch -> {
            ch.setStartDate(ch.getStartDate().truncatedTo(ChronoUnit.SECONDS));
            ch.setEndDate(ch.getEndDate().truncatedTo(ChronoUnit.SECONDS));
        });
    }

    public void whenGetCycleHistory(){
        final String url = String.format("/cycle/history/user/%s/mdn/%s",userId, mdn);
        returnedCycleHistory = testRestTemplate
                .exchange(url, HttpMethod.GET, null, CycleHistoryResponse.class);
    }

    private void thenCorrectCyclesAreReturned() {
        assertNotNull(returnedCycleHistory);
        assertEquals(HttpStatusCode.valueOf(200),returnedCycleHistory.getStatusCode());

        CycleHistoryResponse body = returnedCycleHistory.getBody();
        assertNotNull(body);
        assertEquals(body.getHistory().size(),2);

        List<CycleHistoryDTO> expectedCycles = List.of(toCycleHistoryDTO(existingCycles.get(0)),toCycleHistoryDTO(existingCycles.get(2)));
        body.getHistory().forEach(ch -> {
            ch.setStartDate(ch.getStartDate().truncatedTo(ChronoUnit.SECONDS));
            ch.setEndDate(ch.getEndDate().truncatedTo(ChronoUnit.SECONDS));
        });
        assertThat(body.getHistory(), is(Matchers.contains(allOf(
                        hasProperty("cycleId",is(expectedCycles.get(0).getCycleId())),
                        hasProperty("startDate",is(expectedCycles.get(0).getStartDate())),
                        hasProperty("endDate",is(expectedCycles.get(0).getEndDate()))
                ),
                allOf(
                        hasProperty("cycleId",is(expectedCycles.get(1).getCycleId())),
                        hasProperty("startDate",is(expectedCycles.get(1).getStartDate())),
                        hasProperty("endDate",is(expectedCycles.get(1).getEndDate()))
                )
        )));

    }

    private CycleHistoryDTO toCycleHistoryDTO(final Cycle cycle) {
        return CycleHistoryDTO.builder()
                .cycleId(cycle.getId())
                .startDate(cycle.getStartDate())
                .endDate(cycle.getEndDate()).build();
    }
}
