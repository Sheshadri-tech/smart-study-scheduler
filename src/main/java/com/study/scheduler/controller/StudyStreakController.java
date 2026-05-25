package com.study.scheduler.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.scheduler.entity.StudyStreak;
import com.study.scheduler.repository.StudyStreakRepository;

@Controller
public class StudyStreakController {

    @Autowired
    private StudyStreakRepository studyStreakRepository;

    @GetMapping("/streak")
    public String streakPage(Model model) {

        List<StudyStreak> streaks = studyStreakRepository.findAll();

        StudyStreak streak;

        if (streaks.isEmpty()) {
            streak = new StudyStreak();
            streak.setCurrentStreak(0);
            streak.setLongestStreak(0);
            streak.setLastStudyDate(null);
            studyStreakRepository.save(streak);
        } else {
            streak = streaks.get(0);
        }

        model.addAttribute("streak", streak);

        return "streak";
    }

    @GetMapping("/streak/update")
    public String updateStreak() {

        List<StudyStreak> streaks = studyStreakRepository.findAll();

        StudyStreak streak;

        if (streaks.isEmpty()) {
            streak = new StudyStreak();
            streak.setCurrentStreak(1);
            streak.setLongestStreak(1);
            streak.setLastStudyDate(LocalDate.now());
        } else {
            streak = streaks.get(0);

            LocalDate today = LocalDate.now();
            LocalDate lastDate = streak.getLastStudyDate();

            if (lastDate == null) {
                streak.setCurrentStreak(1);
            } else if (lastDate.plusDays(1).equals(today)) {
                streak.setCurrentStreak(streak.getCurrentStreak() + 1);
            } else if (!lastDate.equals(today)) {
                streak.setCurrentStreak(1);
            }

            if (streak.getCurrentStreak() > streak.getLongestStreak()) {
                streak.setLongestStreak(streak.getCurrentStreak());
            }

            streak.setLastStudyDate(today);
        }

        studyStreakRepository.save(streak);

        return "redirect:/streak";
    }
}