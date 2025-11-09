package org.example.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "login";
    }
    @GetMapping("/index")
    public String index() {
        return "index"; // Spring Boot автоматически найдет index.html в папке templates
    }
}


