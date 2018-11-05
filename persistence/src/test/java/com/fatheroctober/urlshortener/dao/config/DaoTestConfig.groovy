package com.fatheroctober.urlshortener.dao.config

import com.fatheroctober.urlshortener.dao.Dao
import com.fatheroctober.urlshortener.dao.UrlDao
import com.fatheroctober.urlshortener.dao.model.IUrl
import com.fatheroctober.urlshortener.dao.model.IUrlFactory
import com.fatheroctober.urlshortener.dao.model.impl.RedisUrlFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import spock.mock.DetachedMockFactory

@Configuration
class DaoTestConfig {

    def mockFactory = new DetachedMockFactory()

    @Bean
    HashOperations<String, Long, IUrl> hashOperations() {
        mockFactory.Mock(HashOperations)
    }

    @Bean
    ValueOperations<String, IUrl> valueOperations() {
        mockFactory.Mock(ValueOperations)
    }

    @Bean
    RedisTemplate<String, IUrl> redisTemplate() {
        new RedisTestTemplate<String, IUrl>(hashOperations(), valueOperations())
    }

    @Bean
    Dao<IUrl> dao() {
        new UrlDao()
    }

    @Bean
    IUrlFactory urlFactory() {
        new RedisUrlFactory()
    }
}
