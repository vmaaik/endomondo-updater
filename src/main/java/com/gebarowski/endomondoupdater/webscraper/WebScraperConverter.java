package com.gebarowski.endomondoupdater.webscraper;

import com.gebarowski.endomondoupdater.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WebScraperConverter {
    private static final double MILES_TO_KILOMETERS = 1.60934;

    protected List<Integer> convertRanks(final List<String> ranks) {
        return ranks.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    protected List<Long> convertProfileIds(final List<String> profiles) {
        return profiles.stream()
                .map(this::convertToId)
                .collect(Collectors.toList());
    }

    protected List<Double> convertDistances(final List<String> distances) {
        return distances.stream()
                .map(this::parseMile)
                .map(this::convertToKilometers)
                .collect(Collectors.toList());
    }

    private Long convertToId(final String href) {
        return Long.parseLong(href.substring(11));
    }

    private Double parseMile(final String mile) {
        if (mile.equals("(No applicable workouts)") || mile.isEmpty()) {
            log.warn("There might be no applicable workout or miles are empty: " + mile + " Returning 0.00");
            return 0.00;
        } else {
            String mileConverted = mile.replace(" mi", "");
            return Double.parseDouble(mileConverted);
        }
    }

    private Double convertToKilometers(final Double mile) {
        return Utils.round(mile * MILES_TO_KILOMETERS, 2);
    }
}
