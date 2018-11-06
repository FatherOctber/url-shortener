package com.fatheroctober.urlshortener.api

import com.fatheroctober.urlshortener.api.config.UrlShortenerTestConfig
import com.fatheroctober.urlshortener.core.IShortenedUrlService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.ResourceUtils
import spock.lang.Specification

@ContextConfiguration(classes = [UrlShortenerTestConfig.class])
abstract class UrlShortenerControllerTestBase extends Specification {
    @Autowired
    IShortenedUrlService shortenedUrlServiceMock

    @Autowired
    UrlShortenerController urlShortenerController

    @Autowired
    UrlShortenerControllerAdvice urlShortenerControllerAdvice

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlShortenerController)
                .setControllerAdvice(urlShortenerControllerAdvice)
                .setRemoveSemicolonContent(false)
                .build()
    }

    def resource = { resourcePath
        -> ResourceUtils.getFile(this.getClass().getResource(resourcePath)).text
    }
}
