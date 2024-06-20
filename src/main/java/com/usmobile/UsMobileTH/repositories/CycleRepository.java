package com.usmobile.UsMobileTH.repositories;

import java.time.Instant;
import java.util.List;

import com.usmobile.UsMobileTH.models.Cycle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
/**
 * CycleRepository
 *
 * This class defines the database queries for  the 'cycle' collection.
 */
@Repository
public interface CycleRepository extends MongoRepository<Cycle,String> {
    List<Cycle> findAllByUserIdAndMdn(final String userId, final String mdn);

    @Query("{ startDate: { $lte: ?0 }, endDate: { $gte: ?0 }, userId:  ?1, mdn: ?2}")
    Cycle findOneForDateAndUserAndMdn(final Instant now, final String userId,final String mdn);
}
