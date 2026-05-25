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
public class BadgeController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private StudyScheduleRepository scheduleRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/badges")
    public String badgesPage(Model model) {

        long subjectsCount = subjectRepository.count();
        long topicsCount = topicRepository.count();
        long schedulesCount = scheduleRepository.count();
        long remindersCount = reminderRepository.count();

        boolean firstSubjectBadge = subjectsCount >= 1;
        boolean topicStarterBadge = topicsCount >= 3;
        boolean plannerBadge = schedulesCount >= 3;
        boolean reminderBadge = remindersCount >= 1;

        model.addAttribute("firstSubjectBadge", firstSubjectBadge);
        model.addAttribute("topicStarterBadge", topicStarterBadge);
        model.addAttribute("plannerBadge", plannerBadge);
        model.addAttribute("reminderBadge", reminderBadge);

        return "badges";
    }
}