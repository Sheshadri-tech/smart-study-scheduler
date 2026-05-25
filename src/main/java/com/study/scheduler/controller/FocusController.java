package com.study.scheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.scheduler.entity.Topic;
import com.study.scheduler.repository.ReminderRepository;
import com.study.scheduler.repository.StudyScheduleRepository;
import com.study.scheduler.repository.TopicRepository;

@Controller
public class FocusController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudyScheduleRepository scheduleRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/focus")
    public String focusPage(Model model) {

        List<Topic> topics = topicRepository.findAll();

        long totalTopics = topics.size();

        long completedTopics = topics.stream()
                .filter(topic -> "Completed".equalsIgnoreCase(topic.getStatus()))
                .count();

        int completionScore = 0;

        if (totalTopics > 0) {
            completionScore = (int) ((completedTopics * 60) / totalTopics);
        }

        int scheduleScore = scheduleRepository.count() > 0 ? 25 : 0;

        int reminderScore = reminderRepository.count() > 0 ? 15 : 0;

        int focusScore = completionScore + scheduleScore + reminderScore;

        model.addAttribute("focusScore", focusScore);
        model.addAttribute("completionScore", completionScore);
        model.addAttribute("scheduleScore", scheduleScore);
        model.addAttribute("reminderScore", reminderScore);

        return "focus";
    }
}