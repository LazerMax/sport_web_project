package org.example.services;

import org.example.dto.AddLessonDto;
import org.example.dto.ShowLessonStudentDto;
import org.example.dto.ShowLessonTrainerDto;
import org.example.models.entities.Lesson;

import java.util.List;


public interface LessonService {
//    Lesson saveLesson(LessonDto lessonDto);

    List<ShowLessonStudentDto> getAllLessons();

    void addLesson(AddLessonDto addLessonDto);

    List<ShowLessonTrainerDto> getLessonsByTrainerName();

    void deleteLesson(String id);

    ShowLessonStudentDto convertToShowLessonStudentDto(Lesson lesson);

    List<ShowLessonStudentDto> convertToShowLessonStudentDtoList();

    List<ShowLessonStudentDto> getRecordedLessons();

//    Lesson updateLesson(Long id, LessonDto lessonDto)
   Lesson getLessonById(String id);

}
