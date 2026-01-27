package com.neeraj.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ShortenUrlRequest {

    @NotBlank(message = "URL must not be empty")
    @Pattern(
        regexp = "^(http|https)://.*$",
        message = "URL must start with http or https"
    )
    private String originalUrl;

    private Long expiryMinutes;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Long getExpiryMinutes() {
        return expiryMinutes;
    }

    public void setExpiryMinutes(Long expiryMinutes) {
        this.expiryMinutes = expiryMinutes;
    }
}
