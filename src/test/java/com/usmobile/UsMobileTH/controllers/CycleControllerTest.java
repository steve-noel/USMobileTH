package com.usmobile.UsMobileTH.controllers;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomCycle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.usmobile.UsMobileTH.dtos.cycle.CycleHistoryDTO;
import com.usmobile.UsMobileTH.dtos.cycle.CycleHistoryResponse;
import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.services.CycleService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CycleControllerTest {

    @Mock
    private CycleService service;

    @InjectMocks
    private CycleController controller;
    private String userId;
    private String mdn;
    private List<Cycle> cyclesForUserAndMdn;
    private CycleHistoryResponse cycleHistoryResponse;

    @Test
    public void getCycleHistory() {
        givenUserIdAndMdn();
        givenCyclesForAUserAndMdn();
        whenGetCycleHistory();
        thenCycleHistoryResponseReturned();
    }

    public void givenUserIdAndMdn(){
        userId = RandomStringUtils.randomAlphanumeric(10);
        mdn = RandomStringUtils.randomNumeric(10);
    }

    public void givenCyclesForAUserAndMdn(){
        cyclesForUserAndMdn = List.of(randomCycle(),randomCycle(),randomCycle());
        when(service.getCycleHistory(userId,mdn)).thenReturn(cyclesForUserAndMdn);
    }

    public void whenGetCycleHistory(){
        cycleHistoryResponse = controller.getCycleHistory(userId, mdn);
    }

    private void thenCycleHistoryResponseReturned() {
        List<CycleHistoryDTO> cycleHistoryDTOS = cyclesForUserAndMdn.stream().map(c -> toCycleHistoryDto(c)).toList();
        assertEquals(CycleHistoryResponse.builder().history(cycleHistoryDTOS).build(),cycleHistoryResponse);
    }

    private CycleHistoryDTO toCycleHistoryDto(final Cycle cycle) {
        return CycleHistoryDTO.builder()
                .cycleId(cycle.getId())
                .startDate(cycle.getStartDate())
                .endDate(cycle.getEndDate()).build();
    }
}
