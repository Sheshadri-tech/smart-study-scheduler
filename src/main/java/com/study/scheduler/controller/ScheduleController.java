package com.study.scheduler.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.study.scheduler.entity.StudySchedule;
import com.study.scheduler.entity.Topic;
import com.study.scheduler.repository.StudyScheduleRepository;
import com.study.scheduler.repository.TopicRepository;

@Controller
public class ScheduleController {

    @Autowired
    private StudyScheduleRepository scheduleRepository;

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping("/schedule")
    public String schedulePage(Model model) {
        model.addAttribute("schedules", scheduleRepository.findAll());
        model.addAttribute("topics", topicRepository.findAll());
        return "schedule";
    }

    @PostMapping("/schedule/add")
    public String addSchedule(@RequestParam Long topicId,
                              @RequestParam String studyDate,
                              @RequestParam String startTime,
                              @RequestParam String endTime,
                              @RequestParam String notes) {

        Topic topic = topicRepository.findById(topicId).orElse(null);

        StudySchedule schedule = new StudySchedule();
        schedule.setTopic(topic);
        schedule.setStudyDate(LocalDate.parse(studyDate));
        schedule.setStartTime(LocalTime.parse(startTime));
        schedule.setEndTime(LocalTime.parse(endTime));
        schedule.setNotes(notes);

        scheduleRepository.save(schedule);

        return "redirect:/schedule";
    }

    @PostMapping("/schedule/auto-generate")
    public String autoGenerateSchedule() {
    	
    	scheduleRepository.deleteAll();

    	List<Topic> topics =
    	        topicRepository.findAllByOrderByPriorityScoreDesc();
    	
    	
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.of(18, 0);

        for (Topic topic : topics) {

            if (!"Completed".equalsIgnoreCase(topic.getStatus())) {

                StudySchedule schedule = new StudySchedule();
                schedule.setTopic(topic);
                schedule.setStudyDate(date);
                schedule.setStartTime(start);
                schedule.setEndTime(start.plusHours(1));
                schedule.setNotes("Auto generated study session");

                scheduleRepository.save(schedule);

                start = start.plusHours(1);

                if (start.isAfter(LocalTime.of(21, 0))) {
                    date = date.plusDays(1);
                    start = LocalTime.of(18, 0);
                }
            }
        }

        return "redirect:/schedule";
    }
    
    
    @GetMapping("/schedule/delete/{id}")
    public String deleteSchedule(@PathVariable Long id) {

        scheduleRepository.deleteById(id);

        return "redirect:/schedule";
    }
}