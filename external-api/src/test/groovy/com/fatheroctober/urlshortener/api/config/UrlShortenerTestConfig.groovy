package com.fatheroctober.urlshortener.api.config

import com.fatheroctober.urlshortener.api.UrlShortenerController
import com.fatheroctober.urlshortener.api.UrlShortenerControllerAdvice
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import([
        MockedServiceConfig.class
])
class UrlShortenerTestConfig {

    @Bean
    UrlShortenerController urlShortenerController() {
        new UrlShortenerController()
    }

    @Bean
    UrlShortenerControllerAdvice urlShortenerControllerAdvice() {
        new UrlShortenerControllerAdvice()
    }
}
