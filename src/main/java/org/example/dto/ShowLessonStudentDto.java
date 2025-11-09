package org.example.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowLessonStudentDto {
    private String lessonName;
    private LocalDate lessonDate;
    private LocalTime lessonTime;
    private String lessonPlace;
    private String trainerName;
    private String id;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public LocalTime getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(LocalTime lessonTime) {
        this.lessonTime = lessonTime;
    }


    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setLessonPlace(String lessonPlace) {
        this.lessonPlace = lessonPlace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
}
