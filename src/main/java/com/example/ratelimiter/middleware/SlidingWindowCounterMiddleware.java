package com.example.ratelimiter.middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;

public class SlidingWindowCounterMiddleware implements Filter {

    private Timestamp currentTimestamp;
    private int currentRequestsCount;

    public int requestThreshold = 60;
    private int previousTimestampCount = 0;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        timestamp.setNanos(0);

        System.out.println("SlidingWindowCounterMiddleware: Current Time: " + timestamp);

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
        currentRequestsCount++;
    }

    boolean isRequestInLimit() {
        System.out.println("Count: " + ((currentRequestsCount * 60 / 100) + (previousTimestampCount * 40 / 100)));
        return (currentRequestsCount * 60 / 100) + (previousTimestampCount * 40 / 100) < requestThreshold;
    }

    void setNewTimeStamp(Timestamp timestamp) {
        previousTimestampCount = currentRequestsCount;
        currentTimestamp = timestamp;
        currentRequestsCount = 1;
    }

}
