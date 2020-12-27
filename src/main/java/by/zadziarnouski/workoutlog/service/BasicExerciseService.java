package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.BasicExercise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasicExerciseService {
    BasicExercise saveOrUpdate(BasicExercise BasicExercise);

    List<BasicExercise> findAll();

    BasicExercise findByName(String name);

    BasicExercise findById(long id);

    void delete(long id);

}
