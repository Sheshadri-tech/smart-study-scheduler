package com.study.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.scheduler.entity.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}