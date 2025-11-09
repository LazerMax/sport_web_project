package org.example.controllers;

import org.example.models.entities.Users;
import org.example.services.StudentService;
import org.example.services.TrainerService;
import org.example.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UsersService userService;
    private final TrainerService trainerService;
    private final StudentService studentService;

    public RegistrationController(UsersService userService, TrainerService trainerService, StudentService studentService) {
        this.userService = userService;
        this.trainerService = trainerService;
        this.studentService = studentService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userModel", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userModel") Users user, @RequestParam("profile") String profile) {
        // Получаем значение profile из запроса (из select)
        user.setProfile(profile);

        userService.register(user); // Передаем объект Users с установленным profile в сервис

        if (user.getProfile().equals("Тренер")) {
            trainerService.addTrainer(user.getFullName());
        } else {
            studentService.addStudent(user.getFullName());
        }

        return "redirect:/login";
    }
}