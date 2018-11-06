package com.fatheroctober.urlshortener.integrationtest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fatheroctober.urlshortener.application.URLShortenerApplication
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class URLShortenerIntegrationTest extends AbstractIntegrationTest {

    def "application context should start correctly"() {
        expect: "the spring context is not null and it contains the application bean"
        context != null
        context.containsBean(URLShortenerApplication.simpleName)
    }

    def "application shorten long url successfully"() {
        given:
        def url = "https://twitter.com/aloha"

        when:
        def result = mvc.perform(post('/shorturl/v1/shorten')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(resource("shortenURL.rq.json", [URL: url])))

        then:
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(resource("shortenURL.rs.json")))
    }

    def "application returns original url"() {
        given:
        def url = "http://twitter.com/aloha"

        when:
        def resultSave = mvc.perform(post('/shorturl/v1/shorten')
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(resource("shortenURL.rq.json", [URL: url])))
                .andReturn()
        def shortUrlString = new ObjectMapper().readTree(resultSave.response.contentAsString).get("value").textValue()
        def resultForward = mvc.perform(get('/shorturl/v1/' + shortUrlString.split("/").last()))

        then:
        resultForward.andExpect(status().isFound())
                .andExpect(redirectedUrl("http://twitter.com/aloha"))
    }
}
