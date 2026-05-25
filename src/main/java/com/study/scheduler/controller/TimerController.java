package com.study.scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimerController {

    @GetMapping("/timer")
    public String timerPage() {
        return "timer";
    }
}