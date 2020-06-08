package com.gebarowski.endomondoupdater.task;

import com.gebarowski.endomondoupdater.task.updater.GroupChallengeUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private final int groupUpdaterFixedRate = 1800000;

    private final GroupChallengeUpdater groupChallengeUpdater;

    public ScheduledTasks(GroupChallengeUpdater groupChallengeUpdater) {
        this.groupChallengeUpdater = groupChallengeUpdater;
    }

    @Scheduled(fixedRate = groupUpdaterFixedRate)
    public void scheduleGroupChallengeUpdater() {
        this.groupChallengeUpdater.performPageUpdate();
    }
}
