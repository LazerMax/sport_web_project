package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.ShowLessonStudentDto;
import org.example.dto.ShowStudentDto;
import org.example.services.LessonService;
import org.example.services.RecordLessonService;
import org.example.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
@CrossOrigin(origins = "*") // WARNING: Allow all origins for development only!
public class RecordLessonController {

    private final RecordLessonService recordLessonService;
    private final LessonService lessonService;

    @PostMapping(value = "/api/receiveString", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> receiveString(@RequestBody String lessonId) {
        System.out.println("Received string: " + lessonId);
        recordLessonService.addRecord(lessonId);
        String response = "Server received: " + lessonId;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/recordedLessons")
    public List<ShowLessonStudentDto> getRecordedLessonsByStudent() {
        return lessonService.getRecordedLessons();
    }

    @GetMapping("/api/recordedStudents/{lessonId}")
    public List<ShowStudentDto> getRecordedStudentByLesson(@PathVariable String lessonId) {
        return recordLessonService.getRecordedStudents(lessonId);
    }

    @DeleteMapping("/api/deleteRecord/{id}")
    @Transactional
    public ResponseEntity<?> deleteLesson(@PathVariable String id) {
        try {
            recordLessonService.deleteRecordByLessonId(id);
            return new ResponseEntity<>(HttpStatus.OK); // 200 OK
        }catch (Exception e) {
            // Логирование ошибки
            return new ResponseEntity<>("Ошибка при удалении тренировки", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}