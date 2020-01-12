package com.n26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String... args) {
        System.out.println("Server starting...");
        SpringApplication.run(Application.class, args);
    }

}