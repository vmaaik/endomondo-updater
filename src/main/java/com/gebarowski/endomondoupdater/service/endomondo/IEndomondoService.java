package com.gebarowski.endomondoupdater.service.endomondo;

import com.gebarowski.endomondoupdater.model.endomondo.EndomondoUserResult;

import java.util.List;

public interface IEndomondoService {
    List<EndomondoUserResult> getEndomondoResultsByChallengeId(final Long challengeId);
}
