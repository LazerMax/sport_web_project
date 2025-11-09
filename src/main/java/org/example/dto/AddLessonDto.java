package org.example.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddLessonDto {
    private String lessonName;
    private LocalDate lessonDate;
    private LocalTime lessonTime;
    private String lessonPlace;

    @NotEmpty(message = "Lesson name must not be null or empty!")
    public String getLessonName() {
        return lessonName;
    }
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public LocalDate getLessonDate() { return lessonDate; }
    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public LocalTime getLessonTime() { return lessonTime; }
    public void setLessonTime(LocalTime lessonTime) {
        this.lessonTime = lessonTime;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setLessonPlace(String lessonPlace) {
        this.lessonPlace = lessonPlace;
    }
}