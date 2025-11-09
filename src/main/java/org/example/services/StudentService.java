package org.example.services;

import org.example.dto.ShowStudentDto;
import org.example.models.entities.Student;

import java.util.List;

public interface StudentService {
    void addStudent(String studentName);
    Student getStudentByUserName ();
 //   List<ShowStudentDto> getRecordedStudents(String lessonId);
}
