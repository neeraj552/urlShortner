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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class UrlShortenerController {
    private final UrlShortenerService service;
    public UrlShortenerController(UrlShortenerService service){
        this.service = service;
    }
    @PostMapping("/api/v1/shorten")
   public ResponseEntity<ShortenUrlResponse> shorten(
        @Valid @RequestBody ShortenUrlRequest request) {

    String code = service.shortenUrl(
        request.getOriginalUrl(),
        request.getExpiryMinutes()
    );

    String shortUrl = "http://localhost:8080/" + code;

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ShortenUrlResponse(shortUrl));
}
    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException{
        String originalUrl = service.getOriginalUrl(code);
        response.sendRedirect(originalUrl);

    }

}
