package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.RepetitionExercise;
import by.zadziarnouski.workoutlog.model.TimedExercise;
import by.zadziarnouski.workoutlog.repository.TimedExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TimedExerciseServiceImpl implements TimedExerciseService {
    private final TimedExerciseRepository timedExerciseRepository;

    @Autowired
    public TimedExerciseServiceImpl(TimedExerciseRepository timedExerciseRepository) {
        this.timedExerciseRepository = timedExerciseRepository;
    }

    @Override
    public TimedExercise saveOrUpdate(TimedExercise timedExercise) {
        return timedExerciseRepository.save(timedExercise);
    }

    @Override
    public List<TimedExercise> findAll() {
        return timedExerciseRepository.findAll();
    }

    @Override
    public TimedExercise findByName(String name) {
        return timedExerciseRepository.findByName(name);
    }

    @Override
    public TimedExercise findById(long id) {
        Optional<TimedExercise> byId = timedExerciseRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new IllegalArgumentException("exercise with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        timedExerciseRepository.deleteById(id);
    }
}
