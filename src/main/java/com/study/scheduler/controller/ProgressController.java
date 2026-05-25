package com.study.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.scheduler.repository.TopicRepository;

@Controller
public class ProgressController {

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping("/progress")
    public String progressPage(Model model) {

        long totalTopics = topicRepository.count();

        long completedTopics = topicRepository.findAll()
                .stream()
                .filter(topic -> "Completed".equalsIgnoreCase(topic.getStatus()))
                .count();

        long pendingTopics = totalTopics - completedTopics;

        int progressPercent = 0;

        if (totalTopics > 0) {
            progressPercent = (int) ((completedTopics * 100) / totalTopics);
        }

        model.addAttribute("totalTopics", totalTopics);
        model.addAttribute("completedTopics", completedTopics);
        model.addAttribute("pendingTopics", pendingTopics);
        model.addAttribute("progressPercent", progressPercent);

        return "progress";
    }
}