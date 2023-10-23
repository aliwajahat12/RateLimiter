package com.example.ratelimiter.Token;

import com.example.ratelimiter.middleware.TokenBucketMiddleware;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAddScheduler {

    @Scheduled(fixedRate = 1000) // Run every 1 second
    public void printRequestCounts() {
        TokenBucketMiddleware.addNewTokens();
//        TokenBucketMiddleware.printRequestCounts();
    }
}
