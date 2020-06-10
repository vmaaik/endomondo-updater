package com.gebarowski.endomondoupdater.task.updater;

import com.gebarowski.endomondoupdater.page.IndexPage;
import com.gebarowski.endomondoupdater.service.IUserResultService;
import com.gebarowski.endomondoupdater.webscraper.EndomondoWebScraper;
import com.gebarowski.endomondoupdater.webscraper.UserResultCreator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@Component
@Slf4j
public class GroupChallengeUpdater {

    @Value("${index.template.path}")
    private String templatePath;
    @Value("${index.template.export.path}")
    private String exportPath;
    @Value("${challenge.url}")
    private String challengeUrl;

    private final IUserResultService userService;

    private final UserResultCreator userResultCreator;

    private final EndomondoWebScraper endomondoWebScraper;

    private final IndexPage indexPage;

    public GroupChallengeUpdater(IUserResultService userService, UserResultCreator userResultCreator, EndomondoWebScraper endomondoWebScraper, IndexPage indexPage) {
        this.userService = userService;
        this.userResultCreator = userResultCreator;
        this.endomondoWebScraper = endomondoWebScraper;
        this.indexPage = indexPage;
    }

    public void performPageUpdate() {
        final Document document = getDocument();
        final List<Integer> ranks = this.endomondoWebScraper.getRanks(document);
        final List<Long> ids = this.endomondoWebScraper.getProfileIds(document);
        final List<Double> distances = this.endomondoWebScraper.getDistances(document);
        log.info("Attempting to generate group challenge summary page");
        try {
            this.userResultCreator.createUsersResultsFromScrapedData(ranks, ids, distances)
                    .forEach(this.userService::save);
            this.indexPage.exportPage(templatePath, exportPath);
        } catch (Exception ex) {
            log.error("Update has failed");
            ex.printStackTrace();
        }
    }

    @SneakyThrows
    // TODO temp solution must rewrite
    private Document getDocument() {
        Document document = null;
        try {
            log.info("Attempting to create Jsoup Document");
            document = Jsoup.connect(challengeUrl).timeout(60000).get();
            log.info("Jsoup Document created successfully");
        } catch (SocketTimeoutException ex) {
            ex.printStackTrace();
            log.error("Timeout connecting to the page. Trying to connect again...");
            document = Jsoup.connect(challengeUrl).timeout(60000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}