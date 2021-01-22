package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.Exercise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class ExerciseRepositoryTest {
    private final String NAME = "Exercise";

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    public void saveExerciseAndThenGetName() {
        Exercise exercise = new Exercise();
        exercise.setName(NAME);
        Exercise savedExercise = exerciseRepository.save(exercise);
        assertNotNull(savedExercise.getId());
        assertEquals("Exercise", savedExercise.getName());
    }


}