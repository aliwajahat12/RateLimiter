package com.example.ratelimiter.middleware.FixedWindow;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;

@Component
public class FixedWindowCounterMiddleware implements Filter {

    private Timestamp currentTimestamp;
    private int requestsCount;

    public int requestThreshold = 60;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        timestamp.setNanos(0);

        System.out.println("Current Time: " + timestamp);

        if (timestamp.equals(currentTimestamp)) {
            incrementCounter();
            if (!isRequestInLimit()) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(429); // 429 status code (Too Many Requests)
                httpResponse.getWriter().write("Too Many Requests");
                return;
            }
        } else {
            setNewTimeStamp(timestamp);
        }
        chain.doFilter(request, response);
    }

    void incrementCounter() {
        requestsCount++;
    }

    boolean isRequestInLimit() {
        return requestsCount < requestThreshold;
    }

    void setNewTimeStamp(Timestamp timestamp) {
        currentTimestamp = timestamp;
        requestsCount = 1;
    }

}
