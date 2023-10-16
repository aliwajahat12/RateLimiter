package com.example.ratelimiter.middleware;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final Middleware middleware;

    @Bean
    public FilterRegistrationBean<Middleware> ipFilter() {
        FilterRegistrationBean<Middleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(middleware);
        registrationBean.addUrlPatterns("/limited");
        return registrationBean;
    }
}
