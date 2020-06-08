package com.gebarowski.endomondoupdater.page;

import com.gebarowski.endomondoupdater.service.UserResultService;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toMap;

@Component
public class IndexPage extends PageCreator {

    private static final String TEAM_A_NAME = "Drużyna A";

    private static final String TEAM_B_NAME = "Ciosy Mocy aka Ostry Cień Mgły";

    private static Map<String, String> TEMPLATE_KEYS = new HashMap<>();

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final UserResultService userResultService;

    public IndexPage(UserResultService userResultService) {
        this.userResultService = userResultService;
    }

    @Override
    protected String formatTemplate(String template) {
        Double teamAResult = this.userResultService.getGroupResult("A");
        Double teamBResult = this.userResultService.getGroupResult("B");
        Map<String, Double> groupResult = initGroupResultMap(teamAResult, teamBResult);
        Map<String, Double> sorted = sortGroupResult(groupResult);

        TEMPLATE_KEYS.put("TEAM_A", sorted.keySet().toArray()[0].toString());
        TEMPLATE_KEYS.put("TEAM_B", sorted.keySet().toArray()[1].toString());
        TEMPLATE_KEYS.put("RESULT_A", sorted.values().toArray()[0].toString());
        TEMPLATE_KEYS.put("RESULT_B", sorted.values().toArray()[1].toString());
        TEMPLATE_KEYS.put("TIMESTAMP", createTimestamp());

        return super.replaceTemplateKeys(TEMPLATE_KEYS, template);
    }

    // TODO replace with real value from db
    private static String createTimestamp() {
        Timestamp ts = new Timestamp(new Date().getTime());
        return FORMATTER.format(ts);
    }

    private Map<String, Double> sortGroupResult(final Map<String, Double> groupResult) {
        return groupResult
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    private Map<String, Double> initGroupResultMap(final Double teamAResult, final Double teamBResult) {
        Map<String, Double> groupResult = new HashMap<>();
        groupResult.put(TEAM_A_NAME, teamAResult);
        groupResult.put(TEAM_B_NAME, teamBResult);
        return groupResult;
    }
}
