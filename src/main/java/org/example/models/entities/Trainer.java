package org.example.models.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "trainers")
public class Trainer extends BaseEntity {

    @Column(name = "trainer_name")
    private String trainerName;
    private Set<Lesson> lessons;

    @Column(unique = true, nullable = false)
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}
