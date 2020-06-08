package com.gebarowski.endomondoupdater.webscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Configuration
public class JsoupConfiguration {

    @Value("${challange.url}")
    private String challengeUrl;

    //TODO rewrite
    @Bean
    public Document document() throws IOException {
        try {
            return this.connect();
        } catch (SocketTimeoutException ex) {
            return this.connect();
        }
    }

    private Document connect() throws IOException {
        return Jsoup.connect(challengeUrl).get();
    }
}
