package org.example.repositories;

import org.example.models.entities.Lesson;
import org.example.models.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,String> {

    Optional<Lesson> findByLessonName(String lessonName);

    List<Lesson> findAllByTrainer(Trainer trainer);

    @Query("SELECT l FROM Lesson l WHERE l.id = :id") // Corrected JPQL and added alias
    Optional<Lesson> findById(@Param("id") String id);

    @Query("SELECT a FROM Lesson a JOIN RecordLesson b ON a = b.lesson")
    List<Lesson> findAllByIdsFromRecordLesson();

    @Modifying
    @Transactional
    @Query("DELETE Lesson WHERE id=:id")
    void deleteByStringId(String id);

    @Modifying
    @Transactional
    @Query("DELETE Lesson WHERE lessonName=:lessonName")
    void deleteByLessonName(String lessonName);
}