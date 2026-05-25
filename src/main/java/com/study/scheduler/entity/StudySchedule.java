package com.study.scheduler.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "study_schedules")
public class StudySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate studyDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getStudyDate() { return studyDate; }
    public void setStudyDate(LocalDate studyDate) { this.studyDate = studyDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Topic getTopic() { return topic; }
    public void setTopic(Topic topic) { this.topic = topic; }
}