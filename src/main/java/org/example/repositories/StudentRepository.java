package org.example.repositories;

import org.example.models.entities.Lesson;
import org.example.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {

    Optional<Student> findByStudentName(String studentName);

//    @Query("SELECT a FROM Student a JOIN RecordLesson b ON a = b.student")
//    List<Student> findAllByIdsFromRecordStudent();

    @Modifying
    @Transactional
    @Query("DELETE Student WHERE studentName=:studentName")
    void deleteByStudentName(String studentName);
}