package com.study.scheduler.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class StudyStreak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int currentStreak;
    private int longestStreak;
    private LocalDate lastStudyDate;

    public Long getId() {
        return id;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public LocalDate getLastStudyDate() {
        return lastStudyDate;
    }

    public void setLastStudyDate(LocalDate lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }
}