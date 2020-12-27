package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {
    Workout findByTitle(String title);
}
