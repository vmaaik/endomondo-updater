package com.gebarowski.endomondoupdater.task.updater;

import com.gebarowski.endomondoupdater.page.IndexPage;
import com.gebarowski.endomondoupdater.service.IUserResultService;
import com.gebarowski.endomondoupdater.webscraper.EndomondoWebScraper;
import com.gebarowski.endomondoupdater.webscraper.UserResultCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GroupChallengeUpdater {

    @Value("${index.template.path}")
    private String templatePath;
    @Value("${index.template.export.path}")
    private String exportPath;

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
        final List<Integer> ranks = this.endomondoWebScraper.getRanks();
        final List<Long> ids = this.endomondoWebScraper.getProfileIds();
        final List<Double> distances = this.endomondoWebScraper.getDistances();
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
}