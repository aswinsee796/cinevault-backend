package com.aswin.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MyappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyappApplication.class, args);
    }

    // This runs after Spring Boot starts
    @PostConstruct
    public void logPort() {
        System.out.println("Environment PORT variable: " + System.getenv("PORT"));
    }
}
