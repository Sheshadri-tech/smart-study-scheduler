package com.study.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.scheduler.repository.SubjectRepository;
import com.study.scheduler.repository.TopicRepository;
import com.study.scheduler.repository.StudyScheduleRepository;
import com.study.scheduler.repository.ReminderRepository;

@Controller
public class AnalyticsController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudyScheduleRepository scheduleRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/analytics")
    public String analyticsPage(Model model) {

        model.addAttribute("subjectsCount", subjectRepository.count());
        model.addAttribute("topicsCount", topicRepository.count());
        model.addAttribute("schedulesCount", scheduleRepository.count());
        model.addAttribute("remindersCount", reminderRepository.count());

        return "analytics";
    }
}