package com.study.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.study.scheduler.entity.Subject;
import com.study.scheduler.repository.SubjectRepository;

@Controller
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public String subjectsPage(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        return "subjects";
    }

    @PostMapping("/subjects/add")
    public String addSubject(@RequestParam String subjectName,
                             @RequestParam String description) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        subject.setDescription(description);
        subjectRepository.save(subject);
        return "redirect:/subjects";
        
        
    }
    
    @GetMapping("/subjects/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {

        subjectRepository.deleteById(id);

        return "redirect:/subjects";
    }
}