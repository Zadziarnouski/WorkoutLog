package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
 Exercise findByName(String name);
}
