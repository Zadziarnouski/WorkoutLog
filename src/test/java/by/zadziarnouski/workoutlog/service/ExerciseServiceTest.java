package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class ExerciseServiceTest {
    private final String NAME = "Exercise";
    private final Long ID = 10L;

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Test
    public void saveOrUpdate() {
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(new Exercise());
        Exercise exercise = new Exercise();
        Exercise savedOrUpdatedExercise = exerciseService.saveOrUpdate(exercise);
        assertNotNull(savedOrUpdatedExercise);
    }

    @Test
    void findAll() {
        when(exerciseRepository.findAll()).thenReturn(List.of(new Exercise(), new Exercise(), new Exercise()));
        List<Exercise> findAll = exerciseService.findAll();
        assertEquals(3, findAll.size());
    }

    @Test
    void findByName() {
        when(exerciseRepository.findByName(any(String.class))).thenReturn(new Exercise());
        Exercise byName = exerciseService.findByName(NAME);
        assertNotNull(byName);
    }

    @Test
    void findById() {
        when(exerciseRepository.findById(any(Long.class))).thenReturn(Optional.of(new Exercise()));
        Optional<Exercise> byId = Optional.of(exerciseService.findById(ID));
        assertNotNull(byId.get());
    }

    @Test
    void delete() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(exerciseRepository).delete(any(Exercise.class));
        exerciseService.delete(ID);
    }
}