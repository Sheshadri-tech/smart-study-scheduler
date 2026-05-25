package com.study.scheduler.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        if (session.getAttribute("loggedUser") == null) {
            return "redirect:/login";
        }

        return "dashboard";
    }
}