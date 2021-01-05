package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExerciseService {
    Exercise saveOrUpdate(Exercise exercise);

    List<Exercise> findAll();

    Exercise findByName(String name);

    Exercise findById(long id);

    void delete(long id);

}
