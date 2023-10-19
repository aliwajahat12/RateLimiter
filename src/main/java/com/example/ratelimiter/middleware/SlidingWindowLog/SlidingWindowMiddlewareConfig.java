package com.example.ratelimiter.middleware.SlidingWindowLog;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SlidingWindowMiddlewareConfig {
    private final SlidingWindowCounterMiddleware slidingWindowCounterMiddleware;

    @Bean
    public FilterRegistrationBean<SlidingWindowCounterMiddleware> SlidingWindowMiddleware() {
        FilterRegistrationBean<SlidingWindowCounterMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(slidingWindowCounterMiddleware);
        registrationBean.addUrlPatterns("/limited");
        return registrationBean;
    }
}
