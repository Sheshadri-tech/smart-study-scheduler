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
import com.study.scheduler.repository.SubjectRepository;
import com.study.scheduler.repository.TopicRepository;

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

        List<Topic> topics = topicRepository.findAll();

        long subjectsCount = subjectRepository.count();
        long topicsCount = topicRepository.count();
        long schedulesCount = scheduleRepository.count();
        long remindersCount = reminderRepository.count();

        long completedTopics = topics.stream()
                .filter(topic -> "Completed".equalsIgnoreCase(topic.getStatus()))
                .count();

        long pendingTopics = topics.stream()
                .filter(topic -> !"Completed".equalsIgnoreCase(topic.getStatus()))
                .count();

        int completionPercentage = 0;

        if (topicsCount > 0) {
            completionPercentage = (int) ((completedTopics * 100) / topicsCount);
        }

        double averageDifficulty = 0;

        if (!topics.isEmpty()) {
            averageDifficulty = topics.stream()
                    .mapToInt(Topic::getDifficultyLevel)
                    .average()
                    .orElse(0);
        }

        Topic highestPriorityTopic = topics.stream()
                .max(Comparator.comparingInt(Topic::getPriorityScore))
                .orElse(null);

        String highestPriorityTopicName = "No topics available";

        if (highestPriorityTopic != null) {
            highestPriorityTopicName = highestPriorityTopic.getTopicName();
        }
        
        String recommendation = "No recommendation available.";

        if (highestPriorityTopic != null) {
            recommendation = "Study " + highestPriorityTopic.getTopicName()
                    + " first because it has the highest priority score of "
                    + highestPriorityTopic.getPriorityScore()
                    + ", difficulty level "
                    + highestPriorityTopic.getDifficultyLevel()
                    + ", estimated study time of "
                    + highestPriorityTopic.getEstimatedHours()
                    + " hours, and target completion date "
                    + highestPriorityTopic.getTargetCompletionDate()
                    + ".";
        }
        
        
        model.addAttribute("subjectsCount", subjectsCount);
        model.addAttribute("topicsCount", topicsCount);
        model.addAttribute("schedulesCount", schedulesCount);
        model.addAttribute("remindersCount", remindersCount);

        model.addAttribute("completedTopics", completedTopics);
        model.addAttribute("pendingTopics", pendingTopics);
        model.addAttribute("completionPercentage", completionPercentage);
        model.addAttribute("averageDifficulty", String.format("%.1f", averageDifficulty));
        model.addAttribute("highestPriorityTopicName", highestPriorityTopicName);

        model.addAttribute("recommendation", recommendation);
        return "analytics";
    }
}