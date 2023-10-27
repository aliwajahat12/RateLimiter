package com.example.ratelimiter.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisService redisService;

    public int requestThreshold = 5;


    @Override
    public boolean isTimestampEqualToCurrent(Timestamp timestamp) {

        Timestamp currentTimestamp = (Timestamp) redisService.get(Strings.currentTimestamp);
        return currentTimestamp != null && timestamp.equals(currentTimestamp);
    }

    @Override
    public boolean isRequestInLimit() {
        Integer currentRequestsCount = (Integer) redisService.get(Strings.currentRequestsCount);
        Integer previousTimestampCount = (Integer) redisService.get(Strings.previousTimestampCount);

        if (currentRequestsCount == null) currentRequestsCount = 0;
        if (previousTimestampCount == null) previousTimestampCount = 0;

        int count = (currentRequestsCount * 60 / 100) + (previousTimestampCount * 40 / 100);

        System.out.println("Count: " + count);
        return count < requestThreshold;
    }

    @Override
    public void incrementCounter() {
        Integer currentRequestsCount = (Integer) redisService.get(Strings.currentRequestsCount);
        if (currentRequestsCount == null) {
            currentRequestsCount = 0;
        }

        currentRequestsCount++;

        redisService.put(Strings.currentRequestsCount, currentRequestsCount);
    }

    @Override
    public void setNewTimeStamp(Timestamp timestamp) {

        Integer currentRequestsCount = (Integer) redisService.get(Strings.currentRequestsCount);
        if (currentRequestsCount == null) currentRequestsCount = 0;

        redisService.put(Strings.previousTimestampCount, currentRequestsCount);
        redisService.put(Strings.currentTimestamp, timestamp);
        redisService.put(Strings.currentRequestsCount, 1);

    }

    @Override
    public void incrementTotalRequestsCount() {
        Integer totalRequestsCount = (Integer) redisService.get(Strings.totalRequestsCount);
        if (totalRequestsCount == null) totalRequestsCount = 0;

        System.out.println("Total Count: " + totalRequestsCount);

        totalRequestsCount++;

        redisService.put(Strings.totalRequestsCount, totalRequestsCount);
    }
}
