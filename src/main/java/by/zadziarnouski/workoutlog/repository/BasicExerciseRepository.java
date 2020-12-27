package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.BasicExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicExerciseRepository extends JpaRepository<BasicExercise,Long> {
 BasicExercise findByName(String name);
}
