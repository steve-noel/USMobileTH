package com.usmobile.UsMobileTH.controllers;

import java.util.List;

import javax.inject.Inject;

import com.usmobile.UsMobileTH.dtos.cycle.CycleHistoryDTO;
import com.usmobile.UsMobileTH.dtos.cycle.CycleHistoryResponse;
import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.services.CycleService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * CycleController
 *
 * This class defines the REST API for the 'cycle' collection.
 * The API can be found at the "/cycle" URI.
 */
@RestController
@RequestMapping(value = "cycle")
@Slf4j
public class CycleController {
    private final CycleService cycleService;

    @Inject
    public CycleController(final CycleService cycleService) {
        this.cycleService = cycleService;
    }

    /**
     * This method returns the cycle history for a given userId and mdn.
     * The URI for this method is '/history/user/{userId}/mdn/{mdn}'
     * @param userId the id of the user
     * @param mdn the phone number of the user
     * @return the cycles for the given user and phone number
     */
    @RequestMapping(value = "/history/user/{userId}/mdn/{mdn}", method = RequestMethod.GET)
    public CycleHistoryResponse getCycleHistory(@PathVariable(value = "userId") @NotNull String userId, @PathVariable(value = "mdn") @NotNull String mdn) {
        log.info("Retrieving cycle history for user {} and mdn {}",userId,mdn);
        final List<Cycle> cycleHistory = cycleService.getCycleHistory(userId, mdn);
        final List<CycleHistoryDTO>
                cycleHistoryDtos = cycleHistory.stream().map(this::toCycleHistoryDTO).toList();
        return CycleHistoryResponse.builder()
                .history(cycleHistoryDtos).build();
    }

    /**
     * This method converts a {@link Cycle} object into a {@link CycleHistoryDTO} for presentation purposes.
     * @param cycle the cycle document.
     * @return the cycle history object.
     */
    private CycleHistoryDTO toCycleHistoryDTO(final Cycle cycle ) {
        return CycleHistoryDTO.builder()
                .cycleId(cycle.getId())
                .startDate(cycle.getStartDate())
                .endDate(cycle.getEndDate()).build();
    }
}
