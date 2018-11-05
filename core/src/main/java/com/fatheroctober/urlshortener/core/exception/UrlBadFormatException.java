package com.fatheroctober.urlshortener.core.exception;

public class UrlBadFormatException extends RuntimeException {

    public UrlBadFormatException(String message) {
        super(message);
    }

    public static UrlBadFormatException generateFor(String value) {
        return new UrlBadFormatException("Bad format of URL: " + value);
    }
}
