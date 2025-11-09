package org.example.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.ShowLessonStudentDto;
import org.example.dto.ShowLessonTrainerDto;
import org.example.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private final LessonService lessonService;

    public StudentController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/student")
    public String studentPage(Model model) {
        // Здесь можно добавить данные в модель, которые будут отображаться на странице тренера
        model.addAttribute("message", "Welcome, Student!");
        return "student"; // Имя шаблона (student.html)
    }

    @GetMapping("/student/lessons")
    @ResponseBody
    public List<ShowLessonStudentDto> getLessonsForStudent() {
        return lessonService.convertToShowLessonStudentDtoList();
    }
}