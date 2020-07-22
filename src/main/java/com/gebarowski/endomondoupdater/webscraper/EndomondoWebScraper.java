package com.gebarowski.endomondoupdater.webscraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EndomondoWebScraper {

    private final WebScraperConverter webScraperConverter;

    public EndomondoWebScraper(final WebScraperConverter webScraperConverter) {
        this.webScraperConverter = webScraperConverter;
    }

    public List<Integer> getRanks(final Document document) {
        log.info("Attempting to get ranks for challengeId: " + getChallengeIdFromUri(document.baseUri()));
        List<String> ranks = document.getElementsByClass("rank")
                .eachText();
        log.info("Ranks scraped: " + ranks);
        return this.webScraperConverter.convertRanks(ranks);
    }

    public List<Long> getProfileIds(final Document document) {
        log.info("Attempting to get profile ids for challengeId: " + getChallengeIdFromUri(document.baseUri()));
        List<String> profileIds = document.select("div.p-body")
                .select("a.name")
                .eachAttr("href");
        log.info("ProfileIds scraped: " + profileIds);
        return this.webScraperConverter.convertProfileIds(profileIds);
    }

    public List<Double> getDistances(final Document document) {
        log.info("Attempting to get distances for challengeId: " + getChallengeIdFromUri(document.baseUri()));
        List<String> distances = document.getElementsByClass("nose")
                .eachText();
        log.info("Distances scraped: " + distances);
        return this.webScraperConverter.convertDistances(distances);
    }

    private static String getChallengeIdFromUri(final String uri) {
        return uri.substring(37);
    }
}
