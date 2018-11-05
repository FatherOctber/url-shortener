package com.fatheroctober.urlshortener.api.config

import com.fatheroctober.urlshortener.core.IShortenedUrlService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

@Configuration
class MockedServiceConfig {
    def mockFactory = new DetachedMockFactory()

    @Bean
    IShortenedUrlService shortenedUrlService() {
        mockFactory.Mock(IShortenedUrlService)
    }
}
