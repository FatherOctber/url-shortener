package com.fatheroctober.urlshortener.api;

import com.fatheroctober.urlshortener.core.exception.UrlBadFormatException;
import com.fatheroctober.urlshortener.core.exception.UrlNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UrlShortenerControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerControllerAdvice.class);

    @ExceptionHandler(value = {Exception.class})
    public DefaultResponse handleGeneralError(Exception e) {
        logger.error("General error", e);
        return DefaultResponse.generalError();
    }

    @ExceptionHandler(value = {UrlNotFoundException.class})
    public DefaultResponse handleUrlNotFoundError(UrlNotFoundException e) {
        logger.error("Not found error", e);
        return DefaultResponse.notFoundError();
    }

    @ExceptionHandler(value = {UrlBadFormatException.class})
    public DefaultResponse handleUrlBadFormatError(UrlBadFormatException e) {
        logger.error("Bad request error", e);
        return DefaultResponse.badRequestError();
    }
}
