package com.usmobile.UsMobileTH.utils;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import com.usmobile.UsMobileTH.dtos.user.UserCreateRequest;
import com.usmobile.UsMobileTH.dtos.user.UserUpdatedRequest;
import com.usmobile.UsMobileTH.models.Cycle;
import com.usmobile.UsMobileTH.models.DailyUsage;
import com.usmobile.UsMobileTH.models.User;
import org.apache.commons.lang3.RandomStringUtils;

public class TestUtils {
    public static String createRandomEmail() {
        return RandomStringUtils.randomAlphanumeric(6)+"@mail.com";
    }

    public static User randomUser(){
        return User.builder()
                .id(RandomStringUtils.randomAlphanumeric(10))
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .email(TestUtils.createRandomEmail())
                .password(RandomStringUtils.randomAlphanumeric(10)).build();
    }
    public static User randomUser(final Consumer<User> consumer ){
        final User user = randomUser();
        consumer.accept(user);
        return user;
    }

    public static UserCreateRequest randomUserCreateRequest() {
        return UserCreateRequest.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .email(TestUtils.createRandomEmail())
                .password(RandomStringUtils.randomAlphanumeric(10)).build();
    }

    public static UserUpdatedRequest randomUserUpdateRequest(){
        return UserUpdatedRequest.builder()
                .userId(RandomStringUtils.randomAlphanumeric(10))
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .email(TestUtils.createRandomEmail()).build();
    }

    public static UserUpdatedRequest randomUserUpdateRequest(Consumer<UserUpdatedRequest> consumer){
        UserUpdatedRequest userUpdatedRequest = randomUserUpdateRequest();
        consumer.accept(userUpdatedRequest);
        return userUpdatedRequest;
    }

    public static Cycle randomCycle(){
        return Cycle.builder()
                .userId(RandomStringUtils.randomAlphanumeric(10))
                .mdn(RandomStringUtils.randomNumeric(10))
                .startDate(Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt()))
                .endDate(Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt())).build();
    }

    public static Cycle randomCycle(final Consumer<Cycle> consumer){
        final Cycle cycle = randomCycle();
        consumer.accept(cycle);
        return cycle;
    }

    public static DailyUsage randomDailyUsage(){
       return DailyUsage.builder()
               .id(RandomStringUtils.randomAlphanumeric(10))
               .userId(RandomStringUtils.randomAlphanumeric(10))
               .mdn(RandomStringUtils.randomNumeric(10))
               .usageDate(Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt()))
               .usedInMb(new Random().nextInt()).build();
    }

    public static DailyUsage randomDailyUsage(Consumer<DailyUsage> consumer){
        final DailyUsage dailyUsage = randomDailyUsage();
        consumer.accept(dailyUsage);
        return dailyUsage;
    }


}
