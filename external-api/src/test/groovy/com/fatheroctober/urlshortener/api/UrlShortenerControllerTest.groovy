package com.fatheroctober.urlshortener.api

import com.fatheroctober.urlshortener.api.config.UrlShortenerTestConfig
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [UrlShortenerTestConfig.class])
class UrlShortenerControllerTest extends UrlShortenerControllerTestBase {

    def "controller returns original url"() {
        given:
        def shortUrl = '12'

        when:
        def result = mockMvc.perform(get('/shorturl/v1/' + shortUrl))

        then:
        1 * shortenedUrlServiceMock.originalLongUrl(_ as String) >> 'facebook.com/some-of-this'

        expect:
        result.andExpect(status().isFound())
                .andExpect(redirectedUrl("http://facebook.com/some-of-this"))
    }

    def "controller shorten long url"() {
        when:
        def result = mockMvc.perform(post('/shorturl/v1/shorten')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(resource("shortenLongURL.rq.json")))

        then:
        1 * shortenedUrlServiceMock.shorten(*_) >> { arguments ->
            assert arguments[1] == 'https://twitter.com/aloha'
            '/shorturl/v1/7'
        }

        expect:
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(resource("shortenLongURL.rs.json")))
    }

}
