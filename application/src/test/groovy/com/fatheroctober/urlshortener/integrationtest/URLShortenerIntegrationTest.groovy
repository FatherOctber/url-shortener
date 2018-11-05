package com.fatheroctober.urlshortener.integrationtest

import com.fatheroctober.urlshortener.application.URLShortenerApplication
import org.springframework.http.MediaType
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class URLShortenerIntegrationTest extends AbstractIntegrationTest {

    def "application context should start correctly"() {
        expect: "the spring context is not null and it contains the application bean"
        context != null
        context.containsBean(URLShortenerApplication.simpleName)
    }

    def "controller shorten long url successfully"() {
        when:
        def result = mvc.perform(post('/shorturl/v1/shorten')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(resource("shortenURL.rq.json")))

        then:
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(resource("shortenURL.rs.json")))
    }
}
