package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.RepetitionExercise;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RepetitionExerciseService {   //implements BasicExerciseService?
    RepetitionExercise saveOrUpdate(RepetitionExercise repetitionExercise);

    List<RepetitionExercise> findAll();

    RepetitionExercise findByName(String name);

    RepetitionExercise findById(long id);

    void delete(long id);
}
