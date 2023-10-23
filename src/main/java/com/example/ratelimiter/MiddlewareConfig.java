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
    @Value("${active.middleware}")
    private String activeMiddleware;

    @Bean
    public FilterRegistrationBean<Filter> Middleware() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

        System.out.println("Middleware: " + activeMiddleware);

        switch (activeMiddleware) {
            case "token" -> registrationBean.setFilter(new TokenBucketMiddleware());
            case "fixed" -> registrationBean.setFilter(new FixedWindowCounterMiddleware());
            case "log" -> registrationBean.setFilter(new SlidingWindowLogMiddleware());
            case "sliding" -> registrationBean.setFilter(new SlidingWindowCounterMiddleware());
            default -> throw new IllegalArgumentException("Invalid active.middleware value: " + activeMiddleware);
        }
        registrationBean.addUrlPatterns("/limited");
        return registrationBean;
    }
}
