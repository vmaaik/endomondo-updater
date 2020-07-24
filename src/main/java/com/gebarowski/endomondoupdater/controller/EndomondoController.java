package com.gebarowski.endomondoupdater.controller;

import com.gebarowski.endomondoupdater.model.endomondo.EndomondoUserResult;
import com.gebarowski.endomondoupdater.service.endomondo.EndomondoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("endomondo")
public class EndomondoController {

    private final EndomondoService endomondoService;

    public EndomondoController(EndomondoService endomondoService) {
        this.endomondoService = endomondoService;
    }

    @GetMapping("/challenges/{challengeId}/userResults")
    public List<EndomondoUserResult> getEndomondoResultsByChallengeId(@PathVariable Long challengeId) {
        return this.endomondoService.getEndomondoResultsByChallengeId(challengeId);
    }
}
