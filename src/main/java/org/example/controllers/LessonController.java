package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.AddLessonDto;
import org.example.services.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/add")
    public ResponseEntity<?> addLesson(@Valid @RequestBody AddLessonDto lessonModel) {
        lessonService.addLesson(lessonModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Lesson added successfully!"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteLesson(@PathVariable String id) {
        try {
            lessonService.deleteLesson(id);
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK
        }catch (Exception e) {
            // Логирование ошибки
            return new ResponseEntity<>("Ошибка при удалении тренировки", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    record ResponseDto(String message) {}
}