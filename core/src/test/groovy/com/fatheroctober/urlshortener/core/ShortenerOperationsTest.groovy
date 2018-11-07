package com.fatheroctober.urlshortener.core

import com.fatheroctober.urlshortener.core.config.ShortenerServiceTestConfig
import com.fatheroctober.urlshortener.core.exception.UrlBadFormatException
import com.fatheroctober.urlshortener.core.exception.UrlNotFoundException
import com.fatheroctober.urlshortener.core.operation.ShortenerOperationRegistry
import com.fatheroctober.urlshortener.dao.Dao
import com.fatheroctober.urlshortener.dao.model.IUrl
import com.fatheroctober.urlshortener.dao.model.IUrlFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [ShortenerServiceTestConfig.class])
class ShortenerOperationsTest extends Specification {

    @Autowired
    Dao<IUrl> daoMock

    @Autowired
    IUrlFactory urlFactoryMock

    @Autowired
    ShortenerOperationRegistry shortenerOperationRegistry

    def "shorten url successfully"() {
        when:
        def res = shortenerOperationRegistry.shortenUrlOperation().shorten("http://short.com/shorten", "http://www.facebook.com")

        then:
        1 * urlFactoryMock.create('www.facebook.com') >> { String val ->
            createdUrl(val)
        }
        1 * daoMock.save(_ as IUrl) >> 1L
        noExceptionThrown()

        expect:
        res == "http://short.com/b"
    }

    def "get long url successfully"() {
        when:
        def res = shortenerOperationRegistry.getOriginalLongUrlOperation().longUrl("b")

        then:
        1 * daoMock.get(1) >> Optional.of(createdUrl('www.facebook.com'))
        noExceptionThrown()

        expect:
        res == 'www.facebook.com'
    }

    def "long url not found"() {
        when:
        shortenerOperationRegistry.getOriginalLongUrlOperation().longUrl("b")

        then:
        1 * daoMock.get(1) >> Optional.empty()
        thrown(UrlNotFoundException)
    }

    def "bad format of url for shortening"() {
        when:
        shortenerOperationRegistry.shortenUrlOperation().shorten("http://short.com/shorten", "very-bad-url")

        then:
        thrown(UrlBadFormatException)
    }

    def createdUrl = { value -> new TestUrl(value) }

    class TestUrl implements IUrl {
        private String v

        TestUrl(String val) { v = val }

        @Override
        String url() { v }
    }
}
