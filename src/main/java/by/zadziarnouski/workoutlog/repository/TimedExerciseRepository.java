package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.TimedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimedExerciseRepository extends JpaRepository<TimedExercise,Long> {
    TimedExercise findByName(String name);
}
