package com.fatheroctober.urlshortener.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.fatheroctober.urlshortener")
@SpringBootApplication
public class URLShortenerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(URLShortenerApplication.class, args);
    }
}
