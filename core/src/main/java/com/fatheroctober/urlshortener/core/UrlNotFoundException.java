package com.fatheroctober.urlshortener.core;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String message) {
        super(message);
    }

    public static UrlNotFoundException generateFor(String id) {
        return new UrlNotFoundException("URL not found for short URL id: " + id);
    }
}
