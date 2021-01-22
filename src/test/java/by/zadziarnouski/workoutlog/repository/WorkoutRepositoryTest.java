package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class WorkoutRepositoryTest {
    private final String TITLE = "Title";

    @Autowired
    private WorkoutRepository workoutRepository;

    @Test
    public void saveWorkoutAndThenGetTitle() {
        Workout workout = new Workout();
        workout.setTitle(TITLE);
        Workout savedWorkout = workoutRepository.save(workout);
        assertNotNull(savedWorkout.getId());
        assertThat("Title", is(savedWorkout.getTitle()));
    }

}