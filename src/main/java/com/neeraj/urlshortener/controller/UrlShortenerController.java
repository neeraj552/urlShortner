package com.neeraj.urlshortener.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.urlshortener.dto.ShortenUrlRequest;
import com.neeraj.urlshortener.dto.ShortenUrlResponse;
import com.neeraj.urlshortener.service.UrlShortenerService;
import com.neeraj.urlshortener.util.RateLimiter;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class UrlShortenerController {
    private final UrlShortenerService service;
    private final RateLimiter rateLimiter;
    public UrlShortenerController(UrlShortenerService service,RateLimiter rateLimiter){
        this.service = service;
        this.rateLimiter = rateLimiter;
    }
    @Operation(summary = "Create short URL")
    @PostMapping("/api/v1/shorten")
   public ResponseEntity<ShortenUrlResponse> shorten(
        @Valid @RequestBody ShortenUrlRequest request, HttpServletRequest httpRequest) {
    String ip = httpRequest.getRemoteAddr();
    if(!rateLimiter.isAllowed(ip)){
        throw new RuntimeException("Too many request. Try again later");
    }
    String code = service.shortenUrl(
        request.getOriginalUrl(),
        request.getExpiryMinutes()
    );

     return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ShortenUrlResponse(
                        "http://localhost:8080/" + code
                ));
}   
    @Hidden
    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException{
        String originalUrl = service.getOriginalUrl(code);
        response.sendRedirect(originalUrl);

    }
    @GetMapping("/api/v1/analytics/{code}")
    public ResponseEntity<Long> getClicks(@PathVariable String code) {
    return ResponseEntity.ok(service.getClickCount(code));
    }


}
