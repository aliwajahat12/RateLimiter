package com.example.ratelimiter.middleware;

import com.example.ratelimiter.Token.TokenBucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Middleware implements Filter {
    private final Map<String, TokenBucket> ipRequestCountMap = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ipAddress = request.getRemoteAddr();

        TokenBucket bucket = ipRequestCountMap.getOrDefault(ipAddress, null);
        if (bucket == null) {
            bucket = new TokenBucket();
            ipRequestCountMap.put(ipAddress, bucket);
        }

        System.out.println("Request IP address: " + ipAddress + " " + bucket.getTokensSize());

        if (bucket.canRemoveToken()) {
            bucket = bucket.removeToken();
            ipRequestCountMap.put(ipAddress, bucket);
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429); // 429 status code (Too Many Requests)
            httpResponse.getWriter().write("Too Many Requests");
            return;
        }
    }

    public void printRequestCounts() {
        for (Map.Entry<String, TokenBucket> entry : ipRequestCountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getTokensSize());
        }
    }

    public void addNewTokens() {
        for (Map.Entry<String, TokenBucket> entry : ipRequestCountMap.entrySet()) {
            TokenBucket bucket = entry.getValue();
            bucket = bucket.addToken();
            ipRequestCountMap.put(entry.getKey(), bucket);
        }
    }
}
