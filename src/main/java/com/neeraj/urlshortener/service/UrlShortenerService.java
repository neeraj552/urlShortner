package com.neeraj.urlshortener.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.neeraj.urlshortener.entity.ShortUrl;
import com.neeraj.urlshortener.repository.ShortUrlRepository;
import com.neeraj.urlshortener.util.Base62Encoder;
@Service
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private final Base62Encoder encoder;

    public UrlShortenerService(ShortUrlRepository repository,
                               Base62Encoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
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

    ShortUrl url = repository.findByShortCodeAndIsActiveTrue(shortCode)
            .orElseThrow(() -> new RuntimeException("URL not found"));

    if (url.getExpiresAt() != null &&
        url.getExpiresAt().isBefore(LocalDateTime.now())) {

        throw new RuntimeException("URL has expired");
    }

    return url.getOriginalUrl();
}

}
