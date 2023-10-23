package com.example.ratelimiter.middleware;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowLogMiddleware implements Filter {

    private final Map<Timestamp, String> requestLog = new ConcurrentHashMap<>();

    public int requestThreshold = 60;
    Duration duration = Duration.ofSeconds(1);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);

        String ipAddress = request.getRemoteAddr();

        System.out.println("SlidingWindowLogMiddleware: Current Time: " + timestamp + " IP Address: " + ipAddress);

        if (canRequest(timestamp)) {
            addRequestInLog(timestamp, ipAddress);
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429); // 429 status code (Too Many Requests)
            httpResponse.getWriter().write("Too Many Requests");
        }
    }

    boolean canRequest(Timestamp timestamp) {
        Timestamp startTime = new Timestamp(timestamp.getTime() - duration.toMillis());
        int count = 0;

        for (Map.Entry<Timestamp, String> entry : requestLog.entrySet()) {
            Timestamp entryTimestamp = entry.getKey();
            if (entryTimestamp.after(startTime) && entryTimestamp.before(timestamp)) {
                count++;
            }
        }

        return count < requestThreshold;
    }

    void addRequestInLog(Timestamp timestamp, String ipAddress) {
        requestLog.put(timestamp, ipAddress);
    }

}
