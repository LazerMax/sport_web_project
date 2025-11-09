package org.example.services;

import org.example.dto.AddRecordLessonDto;
import org.example.dto.ShowLessonStudentDto;
import org.example.dto.ShowStudentDto;
import org.example.models.entities.RecordLesson;

import java.util.List;

public interface RecordLessonService {
    void addRecord(String lessonName);
    void deleteRecordByLessonId(String lessonId);
    List<ShowStudentDto> getRecordedStudents(String lessonId);
}
