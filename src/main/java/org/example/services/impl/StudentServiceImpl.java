package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.ShowLessonStudentDto;
import org.example.dto.ShowStudentDto;
import org.example.models.entities.Lesson;
import org.example.models.entities.RecordLesson;
import org.example.models.entities.Student;
import org.example.models.entities.Trainer;
import org.example.repositories.RecordLessonRepository;
import org.example.repositories.StudentRepository;
import org.example.services.CurrentUserService;
import org.example.services.RecordLessonService;
import org.example.services.StudentService;
import org.example.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CurrentUserService currentUserService;
    private final UsersService usersService;

    public void addStudent(String studentName) {
        Student student = new Student();
        student.setStudentName(studentName);
        studentRepository.saveAndFlush(student);
    }

    public Student getStudentByUserName(){
        UserDetails userDetails = currentUserService.getCurrentUserDetails();
        String userName = userDetails.getUsername();
        String fullName = usersService.findByUsername(userName).get().getFullName();
        return findByStudentName(fullName);
    }

    public Student findByStudentName(String studentName) {
        return studentRepository.findByStudentName(studentName)
                .orElseThrow(() -> new UsernameNotFoundException("Тренер с таким именем не найден"));
    }
}