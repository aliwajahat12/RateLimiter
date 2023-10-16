package com.example.ratelimiter.Token;

import com.example.ratelimiter.middleware.Middleware;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAddScheduler {

    private final Middleware middleware;

    @Scheduled(fixedRate = 1000) // Run every 1 second
    public void printRequestCounts() {
        middleware.addNewTokens();
        middleware.printRequestCounts();
    }
}
