package com.gebarowski.endomondoupdater.service.endomondo;

import com.gebarowski.endomondoupdater.model.endomondo.EndomondoUserResult;
import com.gebarowski.endomondoupdater.utils.Utils;
import com.gebarowski.endomondoupdater.webscraper.EndomondoWebScraper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class EndomondoService implements IEndomondoService {
    private static final int JSOUP_CONNECTION_TIMEOUT = 60000;
    private static final String ENDOMONDO_CHALLENGES_URL = "https://www.endomondo.com/challenges/";
    private static final Double FIRST_PLACE_BONUS = 1.25;
    private static final Double SECOND_PLACE_BONUS = 1.15;
    private static final Double THIRD_PLACE_BONUS = 1.10;

    private final EndomondoWebScraper endomondoWebScraper;

    public EndomondoService(EndomondoWebScraper endomondoWebScraper) {
        this.endomondoWebScraper = endomondoWebScraper;
    }

    @Override
    public List<EndomondoUserResult> getEndomondoResultsByChallengeId(Long challengeId) {
        final Document document = createJsoupDocument(challengeId);
        final List<Integer> ranks = this.endomondoWebScraper.getRanks(document);
        final List<Long> ids = this.endomondoWebScraper.getProfileIds(document);
        final List<Double> distances = this.endomondoWebScraper.getDistances(document);

        return populateUserResults(challengeId, ranks, ids, distances);
    }

    private List<EndomondoUserResult> populateUserResults(
            final Long challengeId,
            final List<Integer> ranks,
            final List<Long> ids,
            final List<Double> distances) {

        return IntStream.range(0, ranks.size())
                .mapToObj(i -> EndomondoUserResult.builder()
                        .userId(ids.get(i))
                        .challengeId(challengeId)
                        .distance(distances.get(i))
                        .rank(ranks.get(i))
                        .hasExtraPoints(hasExtraPoints(ranks.get(i)))
                        .extraPoints(Utils.round(
                                calculateExtraPoints(ranks.get(i), distances.get(i)),
                                2))
                        .build())
                .collect(Collectors.toList());
    }

    private Boolean hasExtraPoints(final Integer rank) {
        return rank <= 3;
    }

    private Double calculateExtraPoints(final Integer rank, final Double distance) {
        if (hasExtraPoints(rank)) {
            switch (rank) {
                case 1:
                    return FIRST_PLACE_BONUS * distance;
                case 2:
                    return SECOND_PLACE_BONUS * distance;
                case 3:
                    return THIRD_PLACE_BONUS * distance;
            }
        }
        return 0.0;
    }

    private String buildChallengeURL(final Long challengeId) {
        return ENDOMONDO_CHALLENGES_URL + challengeId;
    }

    private Document createJsoupDocument(final Long challengeId) {
        String challengeUrl = buildChallengeURL(challengeId);

        Document document = null;
        try {
            log.info("Attempting to create Jsoup Document");
            document = Jsoup.connect(challengeUrl).timeout(JSOUP_CONNECTION_TIMEOUT).get();
            log.info("Jsoup Document created successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return document;
    }
}
