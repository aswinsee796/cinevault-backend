package com.aswin.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "Backend running!";
    }

    @GetMapping("/ping")
    public String ping() {
        return "Backend is alive!";
    }

    @GetMapping("/favicon.ico")
    public void favicon() {
        // Browser requests for favicon.ico will get HTTP 200 OK with no content
    }
}
