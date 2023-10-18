package com.example.ratelimiter.middleware.TokenBucket;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenBucketMiddlewareConfig {

    private final TokenBucketMiddleware tokenBucketMiddleware;

    @Bean
    public FilterRegistrationBean<TokenBucketMiddleware> ipFilter() {
        FilterRegistrationBean<TokenBucketMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(tokenBucketMiddleware);
        registrationBean.addUrlPatterns("/limited");
        return registrationBean;
    }
}
