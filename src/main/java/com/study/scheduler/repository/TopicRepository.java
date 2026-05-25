package com.study.scheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.scheduler.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {	

    List<Topic> findAllByOrderByPriorityScoreDesc();


}