package com.fatheroctober.urlshortener.core.config

import com.fatheroctober.urlshortener.core.operation.ShortenerOperationRegistry
import com.fatheroctober.urlshortener.dao.Dao
import com.fatheroctober.urlshortener.dao.model.IUrl
import com.fatheroctober.urlshortener.dao.model.IUrlFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.mock.DetachedMockFactory

@Configuration
class ShortenerServiceTestConfig {

    def mockFactory = new DetachedMockFactory()

    @Bean
    Dao<IUrl> dao() {
        mockFactory.Mock(Dao)
    }

    @Bean
    IUrlFactory urlFactory() {
        mockFactory.Mock(IUrlFactory)
    }

    @Bean
    ShortenerOperationRegistry shortenerOperationRegistry() {
        new ShortenerOperationRegistry()
    }
}
