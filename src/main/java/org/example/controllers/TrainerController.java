package org.example.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.dto.AddLessonDto;
import org.example.dto.ShowLessonTrainerDto;
import org.example.models.entities.Lesson;
import org.example.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TrainerController {

    @Autowired
    private final LessonService lessonService;

    public TrainerController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

        @PostMapping("/add")
    public String addLesson(@Valid AddLessonDto lessonModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "redirect:/trainer";
        }
        lessonService.addLesson(lessonModel);
        redirectAttributes.addFlashAttribute("lessonModel", lessonModel);
               redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.lessonModel",
                    bindingResult);

        return "redirect:/trainer";
    }

    @GetMapping("/trainer")
    public String trainerPage(Model model) {
        // Здесь можно добавить данные в модель, которые будут отображаться на странице тренера
        model.addAttribute("message", "Welcome, Trainer!");
        return "trainer"; // Имя шаблона (trainer.html)
    }

    @GetMapping("/trainer/lessons")
    @ResponseBody
    public List<ShowLessonTrainerDto> getLessons() {
        return lessonService.getLessonsByTrainerName();
    }
}