package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Workout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkoutService{
    Workout saveOrUpdate(Workout workout);

    List<Workout> findAll();

    Workout findByTitle(String title);

    Workout findById(long id);

    void delete(long id);
}
