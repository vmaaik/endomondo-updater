package com.gebarowski.endomondoupdater.webscraper;

import com.gebarowski.endomondoupdater.model.UserResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserResultCreator {

    public List<UserResult> createUsersResultsFromScrapedData(
            final List<Integer> ranks,
            final List<Long> ids,
            final List<Double> distances) {

        log.info("Attempting to create users results based on scraped data");
        List<UserResult> userResults = new ArrayList<>();

        for (int i = 0; i < ranks.size(); i++) {
            UserResult userResult = new UserResult();
            userResult.setRank(ranks.get(i));
            userResult.setEndomondoProfileId(ids.get(i));
            userResult.setDistance(distances.get(i));
            userResults.add(userResult);
        }

        log.info(userResults.size() + " users results created");

        return userResults;
    }
}
