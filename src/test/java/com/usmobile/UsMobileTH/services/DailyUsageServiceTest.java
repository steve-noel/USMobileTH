package com.usmobile.UsMobileTH.services;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomCycle;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.repositories.DailyUsageRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DailyUsageServiceTest {

    @Mock
    private CycleService cycleService;

    @Mock
    private DailyUsageRepository repository;

    @InjectMocks
    private DailyUsageService service;
    private Cycle currentCycle;
    private String userId;
    private String mdn;

    @Test
    public void getCurrentCycleDailyUsage() {
        givenUserIdAndMdn();
        givenACurrentCycle();
        whenGetCurrentCycleDailyUsage();
        thenCorrectDailyUsageIsReturned();
    }

    public void givenUserIdAndMdn(){
        userId = RandomStringUtils.randomAlphanumeric(10);
        mdn = RandomStringUtils.randomNumeric(10);
    }

    public void givenACurrentCycle(){
        currentCycle = randomCycle();
        when(cycleService.getCurrentCycle(userId,mdn)).thenReturn(currentCycle);
    }

    private void whenGetCurrentCycleDailyUsage() {
        service.getCurrentCycleDailyUsage(userId,mdn);
    }

    private void thenCorrectDailyUsageIsReturned() {
        verify(cycleService,times(1)).getCurrentCycle(userId,mdn);
        verify(repository,times(1))
                .findAllBetweenStartDateAndEndDateForUser(currentCycle.getStartDate(),currentCycle.getEndDate(),userId,mdn);
    }
}
