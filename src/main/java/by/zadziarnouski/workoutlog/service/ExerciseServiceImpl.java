package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public Exercise saveOrUpdate(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }

    @Override
    public Exercise findByName(String name) {
        return exerciseRepository.findByName(name);
    }

    @Override
    public Exercise findById(long id) {
        Optional<Exercise> byId = exerciseRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new IllegalArgumentException("Exercise with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        exerciseRepository.deleteById(id);
    }
}
