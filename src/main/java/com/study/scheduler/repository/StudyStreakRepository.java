package com.study.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.scheduler.entity.StudyStreak;

public interface StudyStreakRepository
        extends JpaRepository<StudyStreak, Long> {

}