package com.usmobile.UsMobileTH.controllers;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomDailyUsage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.usmobile.UsMobileTH.dtos.daily_usage.CurrentCycleDailyUsageResponse;
import com.usmobile.UsMobileTH.dtos.daily_usage.DailyUsageDTO;
import com.usmobile.UsMobileTH.models.DailyUsage;
import com.usmobile.UsMobileTH.services.DailyUsageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DailyUsageControllerTest {

    @Mock
    private DailyUsageService service;

    @InjectMocks
    private DailyUsageController controller;
    private String userId;
    private String mdn;
    private List<DailyUsage> currentCycleUsage;
    private CurrentCycleDailyUsageResponse returnedCurrentCycleDailyUsage;

    @Test
    public void getCurrentDailyUsage() {
        givenUserIdAndMdn();
        givenACurrentCycleData();
        whenGetCurrentCycleDailyUsage();
        thenCorrenctCycleDailyUsageReturned();
    }

    private void givenACurrentCycleData() {
        currentCycleUsage = List.of(randomDailyUsage(),randomDailyUsage());
        when(service.getCurrentCycleDailyUsage(userId,mdn)).thenReturn(currentCycleUsage);
    }

    public void givenUserIdAndMdn(){
        userId = RandomStringUtils.randomAlphanumeric(10);
        mdn = RandomStringUtils.randomNumeric(10);
    }

    private void whenGetCurrentCycleDailyUsage() {
        returnedCurrentCycleDailyUsage = controller.getCurrentCycleDailyUsage(userId, mdn);
    }

    private void thenCorrenctCycleDailyUsageReturned() {
        final List<DailyUsageDTO> expected = currentCycleUsage.stream().map(this::toDailyUsageDto).toList();
        assertEquals(expected,returnedCurrentCycleDailyUsage.getDailyUsage());
    }

    private DailyUsageDTO toDailyUsageDto(final DailyUsage dailyUsage) {
        return DailyUsageDTO.builder()
                .date(dailyUsage.getUsageDate())
                .dailyUsage(dailyUsage.getUsedInMb()).build();
    }
}
