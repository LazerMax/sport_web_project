package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.AddLessonDto;
import org.example.dto.ShowLessonStudentDto;
import org.example.dto.ShowLessonTrainerDto;
import org.example.models.entities.Lesson;
import org.example.models.entities.Trainer;
import org.example.repositories.LessonRepository;
import org.example.repositories.RecordLessonRepository;
import org.example.services.LessonService;
import org.example.services.TrainerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);

    private final LessonRepository lessonRepository;
    private final TrainerService trainerService;
    private final ModelMapper mapper;
    private final RecordLessonRepository recordLessonRepository;

    @Override
    public List<ShowLessonStudentDto> getAllLessons() {
        return List.of();
    }

    public void addLesson(AddLessonDto lessonModel) {
        logger.info("Adding lesson: {}", lessonModel);

        Lesson lesson = convertDtoToEntity(lessonModel);
        lesson.setTrainer(trainerService.getTrainerByUserName());

        lessonRepository.save(lesson);

        logger.info("Lesson added successfully with id: {}", lesson.getId());
    }

    private Lesson convertDtoToEntity(AddLessonDto lessonModel) {
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonModel.getLessonName());
        lesson.setLessonDate(lessonModel.getLessonDate());
        lesson.setLessonTime(lessonModel.getLessonTime());
        lesson.setLessonPlace(lessonModel.getLessonPlace());
        return lesson;
    }

    public ShowLessonStudentDto convertToShowLessonStudentDto(Lesson lesson) {
        if (lesson == null) {
            return null; // Или выбросить исключение, если lesson не может быть null
        }

        ShowLessonStudentDto dto = new ShowLessonStudentDto();
        dto.setId(lesson.getId().toString()); // Преобразуем UUID в String
        dto.setLessonName(lesson.getLessonName());
        dto.setLessonDate(lesson.getLessonDate());
        dto.setLessonTime(lesson.getLessonTime());
        dto.setLessonPlace(lesson.getLessonPlace());
        if (lesson.getTrainer() != null) {
            dto.setTrainerName(lesson.getTrainer().getTrainerName());
        } else {
            dto.setTrainerName(null); // Или указать значение по умолчанию
        }
        return dto;
    }

    public List<ShowLessonStudentDto> convertToShowLessonStudentDtoList() {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Lesson> lessons = lessonRepository.findAll();

        if (lessons == null) {
            return null;
        }
        return lessons.stream()
                .filter(lesson -> lesson.getLessonDate().isAfter(tomorrow))
                .map(this::convertToShowLessonStudentDto)
                .collect(Collectors.toList());

    }

    public void deleteLesson(String id) {
        lessonRepository.deleteByStringId(id);
    }

    public List<ShowLessonTrainerDto> getLessonsByTrainerName() {

        Trainer trainer = trainerService.getTrainerByUserName();
        List<Lesson> lessons = lessonRepository.findAllByTrainer(trainer);
        List<ShowLessonTrainerDto> lessonDtos = lessons.stream()
                .map(lesson -> mapper.map(lesson, ShowLessonTrainerDto.class))
                .collect(Collectors.toList());

        logger.info("Found {} lessons for trainer: {}", lessonDtos.size(), trainer.getTrainerName());

        return lessonDtos;
    }

    public List<ShowLessonStudentDto> getRecordedLessons() {
        List<Lesson> recordLessons = lessonRepository.findAllByIdsFromRecordLesson();
        List<ShowLessonStudentDto> lessonDtos = recordLessons.stream()
                .map(lesson -> mapper.map(lesson, ShowLessonStudentDto.class))
                .collect(Collectors.toList());

        return lessonDtos;
    }

    public Lesson getLessonById(String id) {
        return lessonRepository.findById(id).orElseThrow();
    }
}