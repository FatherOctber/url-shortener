package com.fatheroctober.urlshortener.dao

import com.fatheroctober.urlshortener.dao.config.DaoTestConfig
import com.fatheroctober.urlshortener.dao.model.IUrl
import com.fatheroctober.urlshortener.dao.model.IUrlFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.ValueOperations
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [DaoTestConfig.class])
class UrlDaoTest extends Specification {

    @Autowired
    HashOperations<String, Long, IUrl> hashOperationsMock

    @Autowired
    ValueOperations<String, IUrl> valueOperationsMock

    @Autowired
    Dao<IUrl> urlDao

    @Autowired
    IUrlFactory urlFactory

    def "save operation finished successfully"() {
        given:
        def url = urlFactory.create("someurl/some-long-value")

        when:
        def res = urlDao.save(url)

        then:
        1 * valueOperationsMock.increment(*_) >> { arguments ->
            assert arguments[1] == 1L
            1
        }
        1 * hashOperationsMock.put(*_) >> { arguments ->
            assert arguments[1] == 1L
            assert arguments[2] == url
        }
        noExceptionThrown()

        expect:
        res == 1L
    }

    def "get operation finished successfully"() {
        given:
        def id = 1L
        def expUrl = urlFactory.create("someurl/some-long-value")

        when:
        def res = urlDao.get(id)

        then:
        1 * hashOperationsMock.get(*_) >> { arguments ->
            assert arguments[1] == 1L
            expUrl
        }
        noExceptionThrown()

        expect:
        res == Optional.of(expUrl)
    }

    def "get operation returns empty url"() {
        when:
        def res = urlDao.get(1L)

        then:
        1 * hashOperationsMock.get(*_) >> { arguments ->
            assert arguments[1] == 1L
            null
        }
        noExceptionThrown()

        expect:
        res == Optional.empty()
    }

    def "null object saving throws exception"() {
        when:
        urlDao.save(null)

        then:
        thrown(RuntimeException)
    }


}
