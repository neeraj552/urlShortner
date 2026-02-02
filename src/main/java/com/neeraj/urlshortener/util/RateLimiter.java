package com.neeraj.urlshortener.util;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class RateLimiter {
    private static final int MAX_REQUEST = 5;
    private static final long WINDOW_SECOND = 60;
    private final Map<String, RequestInfo> store = new ConcurrentHashMap<>();
    public boolean isAllowed(String key){
        long now = Instant.now().getEpochSecond();
        RequestInfo info = store.get(key);
        if(info == null || now - info.windowStart >= WINDOW_SECOND){
            store.put(key, new RequestInfo(1, now));
            return true;
        }
        if(info.count < MAX_REQUEST){
            info.count++;
            return true;
        }
        return false;
    }
    private static class RequestInfo{
        int count;
        long windowStart;
        RequestInfo(int count, long windowStart){
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}
