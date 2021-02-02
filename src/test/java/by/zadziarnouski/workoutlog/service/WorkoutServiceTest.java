package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.Workout;
import by.zadziarnouski.workoutlog.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@DataJpaTest
class WorkoutServiceTest {
    private final String TITLE = "Title";
    private final Long ID = 10L;

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    @Test
    void saveOrUpdate() {
        when(workoutRepository.save(any(Workout.class))).thenReturn(new Workout());
        Workout workout = new Workout();
        Workout savedOrUpdatedExercise = workoutService.saveOrUpdate(workout);
        assertNotNull(savedOrUpdatedExercise);
    }

    @Test
    void findAll() {
        List<Workout> list = new ArrayList<Workout>();
        list.add(new Workout());
        list.add(new Workout());
        list.add(new Workout());
        when(workoutRepository.findAll()).thenReturn(list);
        List<Workout> findAll = workoutService.findAll();
        assertEquals(3, findAll.size());
    }

    @Test
    void findByTitle() {
        when(workoutRepository.findByTitle(any(String.class))).thenReturn(new Workout());
        Workout byTitle = workoutService.findByTitle(TITLE);
        assertNotNull(byTitle);
    }

    @Test
    void findById() {
        when(workoutRepository.findById(any(Long.class))).thenReturn(Optional.of(new Workout()));
        Optional<Workout> byId = Optional.of(workoutService.findById(ID));
        assertNotNull(byId.get());
    }

    @Test
    void delete() {
        doAnswer(new Answer<Void>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(workoutRepository).delete(any(Workout.class));

        workoutService.delete(ID);
    }
}