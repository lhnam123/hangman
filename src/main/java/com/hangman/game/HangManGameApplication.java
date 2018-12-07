package com.hangman.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories
public class HangManGameApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HangManGameApplication.class, args);
    }
}
