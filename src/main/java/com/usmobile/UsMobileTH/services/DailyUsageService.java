package com.usmobile.UsMobileTH.services;

import java.util.List;

import javax.inject.Inject;

import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.models.DailyUsage;
import com.usmobile.UsMobileTH.repositories.DailyUsageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * DailyUsageService
 *
 * This class defines the business logic for working with the 'daily_usage' collection.
 */
@Service
@Slf4j
public class DailyUsageService {

    private final DailyUsageRepository repository;
    private final CycleService cycleService;

    @Inject
    public DailyUsageService(final DailyUsageRepository repository, final CycleService cycleService){
        this.repository = repository;
        this.cycleService = cycleService;
    }
    /**
     * This method returns a users current cycle data usage.
     * @param userId the id of the user
     * @param mdn the phone number of the user
     * @return the daily usage for a users current cycle.
     */
    public List<DailyUsage> getCurrentCycleDailyUsage(final String userId, final String mdn) {
        log.info("Fetching the current cycle for user {} mdn {}",userId,mdn);
        final Cycle currentCycle = cycleService.getCurrentCycle(userId,mdn);

        log.info("Fetching the current cycles usage  for startDate {} endDate {} user {} mdn {}"
                ,currentCycle.getStartDate(),currentCycle.getEndDate(),userId,mdn);
        return repository.findAllBetweenStartDateAndEndDateForUser(currentCycle.getStartDate(),currentCycle.getEndDate(),userId,mdn);
    }
}
