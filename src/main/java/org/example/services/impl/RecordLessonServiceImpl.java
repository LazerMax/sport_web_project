package org.example.services.impl;
import lombok.RequiredArgsConstructor;
import org.example.dto.ShowStudentDto;
import org.example.models.entities.Student;
import org.example.repositories.RecordLessonRepository;
import org.example.models.entities.RecordLesson;
import org.example.services.LessonService;
import org.example.services.RecordLessonService;
import org.example.services.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecordLessonServiceImpl implements RecordLessonService {

    private static final Logger logger = LoggerFactory.getLogger(RecordLessonService.class);
    private final StudentService studentService;
    private final LessonService lessonService;
    private final RecordLessonRepository recordLessonRepository;
    private final ModelMapper mapper;

    public void addRecord(String lessonId) {
        RecordLesson recordLesson = new RecordLesson();
        recordLesson.setStudent(studentService.getStudentByUserName());
        recordLesson.setLesson(lessonService.getLessonById(lessonId));
        recordLessonRepository.save(recordLesson);
    }

    public void deleteRecordByLessonId(String lessonId){
        recordLessonRepository.deleteByLessonAndStudent(lessonService.getLessonById(lessonId), studentService.getStudentByUserName());
    }

    public List <RecordLesson> getRecordLessonsByLesson(String lessonId){
       return recordLessonRepository.findAllByLesson(lessonService.getLessonById(lessonId));
    }

        public List<ShowStudentDto> getRecordedStudents(String lessonId) {
        // 1. Получаем список RecordLesson для данного lessonId
        List<RecordLesson> recordLessons = getRecordLessonsByLesson(lessonId);

        // Обрабатываем случай, когда для данного урока не найдено записей
        if (recordLessons == null || recordLessons.isEmpty()) {
            return List.of(); // Возвращаем пустой список, чтобы избежать NullPointerException или неожиданного поведения
        }

        // 2. Извлекаем students
        List<Student> students = recordLessons.stream()
                .map(RecordLesson::getStudent)
                .collect(Collectors.toList());

        // 4. Преобразуем объекты Student в объекты ShowStudentDto
        List<ShowStudentDto> studentDtos = students.stream()
                .map(student -> mapper.map(student, ShowStudentDto.class))
                .collect(Collectors.toList());

        return studentDtos;
    }

}
