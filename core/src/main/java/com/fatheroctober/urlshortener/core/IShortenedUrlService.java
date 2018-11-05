package com.fatheroctober.urlshortener.core;

public interface IShortenedUrlService {

    String shorten(String localUrl, String longUrl);

    String originalLongUrl(String shortUrl);
}
