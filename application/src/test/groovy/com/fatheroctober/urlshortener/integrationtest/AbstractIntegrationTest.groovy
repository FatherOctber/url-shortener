package com.fatheroctober.urlshortener.integrationtest

import com.fatheroctober.urlshortener.application.URLShortenerApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.util.ResourceUtils
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import java.nio.charset.StandardCharsets

@SpringBootTest(classes = URLShortenerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = Initializer)
@AutoConfigureMockMvc
@Testcontainers
abstract class AbstractIntegrationTest extends Specification {
    static final int TEST_REDIS_PORT = 6379
    static GenericContainer redis = new GenericContainer("redis:3.0.6").withExposedPorts(TEST_REDIS_PORT)

    @Shared
    GenericContainer redisShared = redis //to start redis container

    @Autowired
    MockMvc mvc

    @Autowired
    ApplicationContext context

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "redis_host=" + redis.getContainerIpAddress(),
                    "redis_port=" + redis.getMappedPort(TEST_REDIS_PORT)
            )
            values.applyTo(configurableApplicationContext)
        }
    }

    def resource(String resourcePath, Map substitutions = [:]) {
        def text = ResourceUtils.getFile(this.getClass().getResource(resourcePath)).text
        substitutions.each { String k, String v -> text = text.replace('${' + k + '}', v) }
        text
    }

}
