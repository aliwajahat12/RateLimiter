package com.example.ratelimiter.Redis;

import java.sql.Timestamp;

public interface RedisRepository {

    boolean isTimestampEqualToCurrent(Timestamp timestamp);

    boolean isRequestInLimit();

    void incrementCounter();

    void setNewTimeStamp(Timestamp timestamp);

    void incrementTotalRequestsCount();
}