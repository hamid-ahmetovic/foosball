package com.example.foosball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FoosballApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FoosballApplication.class, args);
    }

}
