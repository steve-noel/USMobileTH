package com.usmobile.UsMobileTH.services;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomCycle;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import com.usmobile.UsMobileTH.repositories.CycleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CycleServiceTest {

    @Mock
    private CycleRepository repository;

    @InjectMocks
    private CycleService service;

    private String userId;
    private String mdn;

    @Test
    void getCycleHistory() {
        givenUserIdAndMdn();
        givenCyclesForAUserAndMdn();
        whenGetCycleHistory();
        thenCorrectCycleHistoryIsReturned();
    }

    @Test
    void getCurrentCycle() {
        givenACurrentCycle();
        whenGetCurrentCycle();
        thenCorrectCurrentCycleIsReturned();
    }

    public void givenUserIdAndMdn(){
        userId = RandomStringUtils.randomAlphanumeric(10);
        mdn = RandomStringUtils.randomNumeric(10);
    }

    public void givenCyclesForAUserAndMdn(){
        when(repository.findAllByUserIdAndMdn(userId,mdn)).thenReturn(List.of(randomCycle(),randomCycle()));
    }

    public void givenACurrentCycle(){
        when(repository.findOneForDateAndUserAndMdn(any(Instant.class),eq(userId),eq(mdn))).thenReturn(randomCycle());
    }

    private void whenGetCurrentCycle() {
        service.getCurrentCycle(userId,mdn);
    }

    public void whenGetCycleHistory(){
        service.getCycleHistory(userId, mdn);
    }

    private void thenCorrectCycleHistoryIsReturned() {
        verify(repository,times(1)).findAllByUserIdAndMdn(userId,mdn);
    }

    private void thenCorrectCurrentCycleIsReturned() {
        verify(repository,times(1)).findOneForDateAndUserAndMdn(any(Instant.class),eq(userId),eq(mdn));
    }

}
