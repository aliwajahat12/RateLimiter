package com.example.ratelimiter;

import com.example.ratelimiter.middleware.FixedWindowCounterMiddleware;
import com.example.ratelimiter.middleware.SlidingWindowCounterMiddleware;
import com.example.ratelimiter.middleware.SlidingWindowLogMiddleware;
import com.example.ratelimiter.middleware.TokenBucketMiddleware;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MiddlewareConfig {
    private final TokenBucketMiddleware tokenBucketMiddleware;
    private final FixedWindowCounterMiddleware fixedWindowCounterMiddleware;
    private final SlidingWindowLogMiddleware slidingWindowLogMiddleware;
    private final SlidingWindowCounterMiddleware slidingWindowCounterMiddleware;

    @Value("${active.middleware}")
    private String activeMiddleware;

    @Bean
    public FilterRegistrationBean<Filter> Middleware() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

        System.out.println("Middleware: " + activeMiddleware);

        switch (activeMiddleware) {
            case "token" -> registrationBean.setFilter(tokenBucketMiddleware);
            case "fixed" -> registrationBean.setFilter(fixedWindowCounterMiddleware);
            case "log" -> registrationBean.setFilter(slidingWindowLogMiddleware);
            case "sliding" -> registrationBean.setFilter(slidingWindowCounterMiddleware);
            default -> throw new IllegalArgumentException("Invalid active.middleware value: " + activeMiddleware);
        }
        registrationBean.addUrlPatterns("/limited");
        return registrationBean;
    }
}
