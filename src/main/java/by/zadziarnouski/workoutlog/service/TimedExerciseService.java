package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.TimedExercise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TimedExerciseService {   //implements BasicExerciseService?
    TimedExercise saveOrUpdate(TimedExercise timedExercise);

    List<TimedExercise> findAll();

    TimedExercise findByName(String name);

    TimedExercise findById(long id);

    void delete(long id);
}
