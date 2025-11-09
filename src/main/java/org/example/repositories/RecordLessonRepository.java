package org.example.repositories;

import org.example.models.entities.Lesson;
import org.example.models.entities.RecordLesson;
import org.example.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecordLessonRepository extends JpaRepository<RecordLesson,String> {

    List <RecordLesson> findAllByStudent (Student student);
    List <RecordLesson> findAllByLesson (Lesson lesson);

    @Modifying
    @Transactional
    @Query("DELETE RecordLesson WHERE lesson=:lesson AND student=:student")
    void deleteByLessonAndStudent(Lesson lesson, Student student);
}
