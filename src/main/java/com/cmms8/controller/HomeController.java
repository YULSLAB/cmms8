package com.cmms8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "auth/LoginForm";
    }

    @GetMapping({"/", "/home"})
    public String home() {
        return "home/blank";
    }
}
