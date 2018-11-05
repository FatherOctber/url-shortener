package com.fatheroctober.urlshortener.core.operation;

public interface IShortenUrlOperation {
    String shorten(String localUrl, String longUrl);
}
