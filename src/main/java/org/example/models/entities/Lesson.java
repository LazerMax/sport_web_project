package org.example.models.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;


@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    private String lessonName;
    private LocalDate lessonDate;
    private LocalTime lessonTime;
    private String lessonPlace;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(nullable = false)
    public String getLessonName() {
        return lessonName;
    }
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    @Column(nullable = false)
    public LocalDate getLessonDate() {
        return lessonDate;
    }
    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    @Column(nullable = false)
    public LocalTime getLessonTime() {
        return lessonTime;
    }
    public void setLessonTime(LocalTime lessonTime) {
        this.lessonTime = lessonTime;
    }

    @ManyToOne
    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getLessonPlace() {
        return lessonPlace;
    }

    public void setLessonPlace(String lessonPlace) {
        this.lessonPlace = lessonPlace;
    }
}