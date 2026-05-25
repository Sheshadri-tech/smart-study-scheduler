package com.study.scheduler.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.study.scheduler.entity.Subject;
import com.study.scheduler.entity.Topic;
import com.study.scheduler.repository.SubjectRepository;
import com.study.scheduler.repository.TopicRepository;

@Controller
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/topics")
    public String topicsPage(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "topics";
    }

    @PostMapping("/topics/add")
    public String addTopic(@RequestParam String topicName,
                           @RequestParam String status,
                           @RequestParam int difficultyLevel,
                           @RequestParam int estimatedHours,
                           @RequestParam String targetCompletionDate,
                           @RequestParam Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        LocalDate targetDate = LocalDate.parse(targetCompletionDate);

        long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), targetDate);
        int urgencyScore = daysRemaining <= 3 ? 50 : daysRemaining <= 7 ? 30 : 10;

        int priorityScore = (difficultyLevel * 20) + (estimatedHours * 5) + urgencyScore;

        Topic topic = new Topic();
        topic.setTopicName(topicName);
        topic.setStatus(status);
        topic.setDifficultyLevel(difficultyLevel);
        topic.setEstimatedHours(estimatedHours);
        topic.setTargetCompletionDate(targetDate);
        topic.setPriorityScore(priorityScore);
        topic.setSubject(subject);

        topicRepository.save(topic);

        return "redirect:/topics";
    }

    @GetMapping("/topics/delete/{id}")
    public String deleteTopic(@PathVariable Long id) {
        topicRepository.deleteById(id);
        return "redirect:/topics";
    }
}