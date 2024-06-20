package com.usmobile.UsMobileTH.config.dbmigrations;

import java.time.Instant;
import java.util.List;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.models.DailyUsage;
import com.usmobile.UsMobileTH.models.User;
import com.usmobile.UsMobileTH.repositories.CycleRepository;
import com.usmobile.UsMobileTH.repositories.DailyUsageRepository;
import com.usmobile.UsMobileTH.repositories.UserRepository;

@ChangeLog(order = "001")
public class DatabaseInitChangeLog {

    @ChangeSet(order = "001", id = "seedDatabase", author = "SteveNoel")
    public void seedDatabase(UserRepository userRepository,CycleRepository cycleRepository,DailyUsageRepository dailyUsageRepository) {
        final List<User> users = userRepository.insert(List.of(
                User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("jdoe@email.com")
                        .password("secret").build(),
                User.builder()
                        .firstName("Mary")
                        .lastName("Poppins")
                        .email("marypoppins@email.com")
                        .password("ppwprd").build(),
                User.builder()
                        .firstName("Ice")
                        .lastName("T")
                        .email("iceT@email.com")
                        .password("p123123").build()
        ));

        final List<Cycle> cycles = cycleRepository.insert(List.of(
                Cycle.builder()
                        .mdn("123123123")
                        .startDate(Instant.parse("2024-06-01T00:00:00.00Z"))
                        .endDate(Instant.parse("2024-06-30T23:59:59.00Z"))
                        .userId(users.get(0).getId()).build(),
                Cycle.builder()
                        .mdn("7827089")
                        .startDate(Instant.parse("2024-05-01T00:00:00.00Z"))
                        .endDate(Instant.parse("2024-05-31T23:59:59.00Z"))
                        .userId(users.get(1).getId()).build(),
                Cycle.builder()
                        .mdn("87549032")
                        .startDate(Instant.parse("2024-01-01T00:00:00.00Z"))
                        .endDate(Instant.parse("2024-01-31T23:59:59.00Z"))
                        .userId("test").build()
        ));

        dailyUsageRepository.insert(List.of(
                DailyUsage.builder()
                        .mdn("123123123")
                        .userId(users.get(0).getId())
                        .usageDate(Instant.parse("2024-06-01T00:00:00.00Z"))
                        .usedInMb(1).build(),
                DailyUsage.builder()
                        .mdn("123123123")
                        .userId(users.get(0).getId())
                        .usageDate(Instant.parse("2024-06-02T00:00:00.00Z"))
                        .usedInMb(1).build(),
                DailyUsage.builder()
                        .mdn("123123123")
                        .userId(users.get(0).getId())
                        .usageDate(Instant.parse("2024-03-02T00:00:00.00Z"))
                        .usedInMb(1).build()
        ));
    }
}
