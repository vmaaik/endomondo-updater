package com.gebarowski.endomondoupdater.model.endomondo;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EndomondoUserResult {
    Long challengeId;
    Long userId;
    Double distance;
    Integer rank;
    Boolean hasExtraPoints;
    Double extraPoints;
}
