package com.usmobile.UsMobileTH.repositories;

import java.time.Instant;
import java.util.List;

import com.usmobile.UsMobileTH.models.DailyUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * DailyUsageRepository
 *
 * This class defines the database queries for  the 'daily_usage' collection.
 */
@Repository
public interface DailyUsageRepository extends MongoRepository<DailyUsage,String> {
    @Query("{ usageDate: { $lte: ?1 ,$gte: ?0 }, userId:  ?2, mdn: ?3 }")
    List<DailyUsage> findAllBetweenStartDateAndEndDateForUser(final Instant startDate, final Instant endDate,final String userId, final String mdn);
}
