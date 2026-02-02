package com.neeraj.urlshortener.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.neeraj.urlshortener.entity.ShortUrl;
import com.neeraj.urlshortener.repository.ShortUrlRepository;
import com.neeraj.urlshortener.util.Base62Encoder;
@Service
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private final Base62Encoder encoder;
    private final RedisTemplate<String, String> redisTemplate;

    public UrlShortenerService(ShortUrlRepository repository,
                               Base62Encoder encoder,
                               RedisTemplate<String, String> redisTemplate) {                        
        this.repository = repository;
        this.encoder = encoder;
        this.redisTemplate = redisTemplate;
    }
    private String redisKey(String code) {
    return "short:" + code;
    }

    public String shortenUrl(String originalUrl, Long expiryMinutes) {

        String code = encoder.encode(System.nanoTime());

        ShortUrl entity = new ShortUrl();
        entity.setOriginalUrl(originalUrl);
        entity.setShortCode(code);
        if(expiryMinutes != null && expiryMinutes > 0){
            entity.setExpiresAt(
                LocalDateTime.now().plusMinutes(expiryMinutes)
            );
        }
        repository.save(entity);  
        return code;
    }

   public String getOriginalUrl(String shortCode) {
    String cachedUrl = redisTemplate.opsForValue().get(redisKey(shortCode));
    if(cachedUrl != null) return cachedUrl;

    ShortUrl url = repository.findByShortCodeAndIsActiveTrue(shortCode)
            .orElseThrow(() -> new RuntimeException("URL not found"));

    if (url.getExpiresAt() != null &&
        url.getExpiresAt().isBefore(LocalDateTime.now())) {

        throw new RuntimeException("URL has expired");
    }
    url.setClickCount(url.getClickCount() + 1);
    repository.save(url);
    if (url.getExpiresAt() != null) {

        long ttlSeconds = Duration.between(
                LocalDateTime.now(),
                url.getExpiresAt()
        ).getSeconds();

        if (ttlSeconds > 0) {
            redisTemplate.opsForValue().set(
                    redisKey(shortCode),
                    url.getOriginalUrl(),
                    ttlSeconds,
                    TimeUnit.SECONDS
            );
        }

    } else {
        redisTemplate.opsForValue()
                .set(redisKey(shortCode), url.getOriginalUrl());
    }

    return url.getOriginalUrl();
}
public Long getClickCount(String shortCode) {
    return repository.findByShortCode(shortCode)
            .orElseThrow(() -> new RuntimeException("URL not found"))
            .getClickCount();
}

}
