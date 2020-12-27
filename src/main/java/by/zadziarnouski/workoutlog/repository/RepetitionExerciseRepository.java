package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.RepetitionExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepetitionExerciseRepository extends JpaRepository<RepetitionExercise,Long> {
    RepetitionExercise findByName(String name);
}
