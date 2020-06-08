package com.gebarowski.endomondoupdater.webscraper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EndomondoWebScraper {

    private final Document document;
    private final String challangeId;
    private final WebScraperConverter webScraperConverter;

    public EndomondoWebScraper(final Document document, final WebScraperConverter webScraperConverter) {
        this.document = document;
        this.webScraperConverter = webScraperConverter;
        this.challangeId = getChallengeIdFromUri(document.baseUri());
    }

    public List<Integer> getRanks() {
        log.info("Attempting to get ranks for challengeId: " + challangeId);
        List<String> ranks = this.document.getElementsByClass("rank")
                .eachText();
        log.info("Ranks scraped: " + ranks);
        return this.webScraperConverter.convertRanks(ranks);
    }

    public List<Long> getProfileIds() {
        log.info("Attempting to get profile ids for challengeId: " + challangeId);
        List<String> profileIds = this.document.select("div.p-body")
                .select("a.name")
                .eachAttr("href");
        log.info("ProfileIds scraped: " + profileIds);
        return this.webScraperConverter.convertProfileIds(profileIds);
    }

    public List<Double> getDistances() {
        log.info("Attempting to get distances for challengeId: " + challangeId);
        List<String> distances = this.document.getElementsByClass("nose")
                .eachText();
        log.info("Distances scraped: " + distances);
        return this.webScraperConverter.convertDistances(distances);
    }

    private static String getChallengeIdFromUri(final String uri) {
        log.info("Getting challengeId from URI" + uri);
        return uri.substring(37);
    }
}
