package com.usmobile.UsMobileTH.controllers;

import java.util.List;

import javax.inject.Inject;

import com.usmobile.UsMobileTH.dtos.daily_usage.CurrentCycleDailyUsageResponse;
import com.usmobile.UsMobileTH.dtos.daily_usage.DailyUsageDTO;
import com.usmobile.UsMobileTH.models.DailyUsage;
import com.usmobile.UsMobileTH.services.DailyUsageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * DailyUsageController
 *
 * This class defines the REST API for the 'daily_usage' collection.
 * The API can be found at the "/daily_usage" URI.
 */
@RestController
@RequestMapping(value = "daily_usage")
@Slf4j
public class DailyUsageController {
    private final DailyUsageService dailyUsageService;

    @Inject
    public DailyUsageController(final DailyUsageService dailyUsageService) {
        this.dailyUsageService = dailyUsageService;
    }

    /**
     * This method returns a users current cycles daily usage.
     * The URI for this method is '/current_cycle_usage/user/{userId}/mdn/{mdn}'
     * @param userId the id of the user
     * @param mdn the phone number of the user
     * @return the daily usage for the users current cycle.
     */
    @RequestMapping(value = "/current_cycle_usage/user/{userId}/mdn/{mdn}", method = RequestMethod.GET)
    public CurrentCycleDailyUsageResponse getCurrentCycleDailyUsage(@PathVariable(value = "userId") String userId, @PathVariable(value = "mdn") String mdn) {
        log.info("Fetching current cycles daily usage for user {} mdn {}",userId,mdn);
        final List<DailyUsage> dailyUsageForCurrentCycle = dailyUsageService.getCurrentCycleDailyUsage(userId,mdn);
        final List<DailyUsageDTO> dailyUsageDtos = dailyUsageForCurrentCycle.stream().map(this::toDailyUsageDto).toList();
        return CurrentCycleDailyUsageResponse.builder().dailyUsage(dailyUsageDtos).build();
    }

    /**
     * This method converts a {@link DailyUsage} object into a {@link DailyUsageDTO} for presentation purposes.
     * @param dailyUsage the daily_usage document.
     * @return the DailyUsageDTO object.
     */
    private DailyUsageDTO toDailyUsageDto(final DailyUsage dailyUsage) {
        return DailyUsageDTO.builder()
                .date(dailyUsage.getUsageDate())
                .dailyUsage(dailyUsage.getUsedInMb()).build();
    }

}
