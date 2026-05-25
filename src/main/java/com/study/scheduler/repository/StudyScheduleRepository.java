package com.study.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.scheduler.entity.StudySchedule;

public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
}