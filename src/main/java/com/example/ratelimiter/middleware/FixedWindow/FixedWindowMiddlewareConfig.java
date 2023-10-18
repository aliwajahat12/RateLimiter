package com.example.ratelimiter.middleware.FixedWindow;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FixedWindowMiddlewareConfig {
    private final FixedWindowCounterMiddleware fixedWindowCounterMiddleware;

    @Bean
    public FilterRegistrationBean<FixedWindowCounterMiddleware> FixedWindowMiddleware() {
        FilterRegistrationBean<FixedWindowCounterMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(fixedWindowCounterMiddleware);
        registrationBean.addUrlPatterns("/limited");
        return registrationBean;
    }
}
