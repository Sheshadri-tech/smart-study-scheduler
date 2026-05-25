package com.study.scheduler.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.study.scheduler.entity.Reminder;
import com.study.scheduler.repository.ReminderRepository;

@Controller
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping("/reminders")
    public String remindersPage(Model model) {
        model.addAttribute("reminders", reminderRepository.findAll());
        return "reminders";
    }

    @PostMapping("/reminders/add")
    public String addReminder(@RequestParam String title,
                              @RequestParam String reminderDate,
                              @RequestParam String reminderTime,
                              @RequestParam String message) {

        Reminder reminder = new Reminder();
        reminder.setTitle(title);
        reminder.setReminderDate(LocalDate.parse(reminderDate));
        reminder.setReminderTime(LocalTime.parse(reminderTime));
        reminder.setMessage(message);

        reminderRepository.save(reminder);

        return "redirect:/reminders";
    }
    
    
    @GetMapping("/reminders/delete/{id}")
    public String deleteReminder(@PathVariable Long id) {

        reminderRepository.deleteById(id);

        return "redirect:/reminders";
    }
}