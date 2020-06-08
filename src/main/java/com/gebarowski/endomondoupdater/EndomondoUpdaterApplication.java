package com.gebarowski.endomondoupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EndomondoUpdaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndomondoUpdaterApplication.class, args);
    }

}
