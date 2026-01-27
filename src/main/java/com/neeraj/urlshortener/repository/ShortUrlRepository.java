package com.neeraj.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neeraj.urlshortener.entity.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCodeAndIsActiveTrue(String shortCode);

}
