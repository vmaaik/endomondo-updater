package com.gebarowski.endomondoupdater.controller;

import com.gebarowski.endomondoupdater.model.UserResult;
import com.gebarowski.endomondoupdater.service.IUserResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserResultController {
    @Autowired
    IUserResultService userService;

    @PutMapping()
    public ResponseEntity<UserResult> updateArticle(@RequestBody UserResult userResult) {
        userService.save(userResult);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity getLatestTotal() {
        return new ResponseEntity<>(userService.getLatestTotal(), HttpStatus.OK);
    }
}
