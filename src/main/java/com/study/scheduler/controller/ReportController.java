package com.study.scheduler.controller;

import java.util.Comparator;
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
public class ReportController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudyScheduleRepository scheduleRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/report")
    public String reportPage(Model model) {

        List<Topic> topics = topicRepository.findAll();

        long totalTopics = topicRepository.count();
        long completedTopics = topics.stream()
                .filter(topic -> "Completed".equalsIgnoreCase(topic.getStatus()))
                .count();

        long pendingTopics = totalTopics - completedTopics;

        int completionPercentage = 0;

        if (totalTopics > 0) {
            completionPercentage = (int) ((completedTopics * 100) / totalTopics);
        }

        Topic highestPriorityTopic = topics.stream()
                .max(Comparator.comparingInt(Topic::getPriorityScore))
                .orElse(null);

        String highestPriorityTopicName = highestPriorityTopic != null
                ? highestPriorityTopic.getTopicName()
                : "No topics available";

        model.addAttribute("totalTopics", totalTopics);
        model.addAttribute("completedTopics", completedTopics);
        model.addAttribute("pendingTopics", pendingTopics);
        model.addAttribute("completionPercentage", completionPercentage);
        model.addAttribute("totalSchedules", scheduleRepository.count());
        model.addAttribute("totalReminders", reminderRepository.count());
        model.addAttribute("highestPriorityTopicName", highestPriorityTopicName);

        return "report";
    }
}