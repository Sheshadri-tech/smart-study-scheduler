package com.study.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.scheduler.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}