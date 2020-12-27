package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.RepetitionExercise;
import by.zadziarnouski.workoutlog.repository.RepetitionExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RepetitionExerciseServiceImpl implements RepetitionExerciseService {
    private final RepetitionExerciseRepository repetitionExerciseRepository;

    @Autowired
    public RepetitionExerciseServiceImpl(RepetitionExerciseRepository repetitionExerciseRepository) {
        this.repetitionExerciseRepository = repetitionExerciseRepository;
    }

    @Override
    public RepetitionExercise saveOrUpdate(RepetitionExercise repetitionExercise) {
        return repetitionExerciseRepository.save(repetitionExercise);
    }

    @Override
    public List<RepetitionExercise> findAll() {
        return repetitionExerciseRepository.findAll();
    }

    @Override
    public RepetitionExercise findByName(String name) {
        return repetitionExerciseRepository.findByName(name);
    }

    @Override
    public RepetitionExercise findById(long id) {
        Optional<RepetitionExercise> byId = repetitionExerciseRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new IllegalArgumentException("exercise with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        repetitionExerciseRepository.deleteById(id);
    }
}
