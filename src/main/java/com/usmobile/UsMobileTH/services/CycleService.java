package com.usmobile.UsMobileTH.services;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.repositories.CycleRepository;
import org.springframework.stereotype.Service;

/**
 * CycleService
 *
 * This class defines the business logic for working with the 'cycle' collection.
 */
@Service
public class CycleService {

    private final CycleRepository repository;

    @Inject
    public CycleService(final CycleRepository repository) {
        this.repository = repository;
    }

    /**
     * This method returns a users current cycle.
     * @param userId the id of the user
     * @param mdn the phone number of the user
     * @return the current cycle for the user.
     */
    public Cycle getCurrentCycle(final String userId, final String mdn) {
        return repository.findOneForDateAndUserAndMdn(Instant.now(),userId,mdn);
    }
    /**
     * This method returns a users cycle history.
     * @param userId the id of the user
     * @param mdn the phone number of the user
     * @return the cycle history for the user.
     */
    public List<Cycle> getCycleHistory(final String userId, final String mdn) {
        return repository.findAllByUserIdAndMdn(userId,mdn);
    }

}
