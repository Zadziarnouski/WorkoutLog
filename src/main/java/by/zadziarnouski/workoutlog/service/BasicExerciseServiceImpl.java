package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.BasicExercise;
import by.zadziarnouski.workoutlog.repository.BasicExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BasicExerciseServiceImpl implements BasicExerciseService {

    private final BasicExerciseRepository basicExerciseRepository;

    @Autowired
    public BasicExerciseServiceImpl(BasicExerciseRepository basicExerciseRepository) {
        this.basicExerciseRepository = basicExerciseRepository;
    }

    @Override
    public BasicExercise saveOrUpdate(BasicExercise basicExercise) {
        return basicExerciseRepository.save(basicExercise);
    }

    @Override
    public List<BasicExercise> findAll() {
        return basicExerciseRepository.findAll();
    }

    @Override
    public BasicExercise findByName(String name) {
        return basicExerciseRepository.findByName(name);
    }

    @Override
    public BasicExercise findById(long id) {
        Optional<BasicExercise> byId = basicExerciseRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }else {
            throw new IllegalArgumentException("Exercise with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        basicExerciseRepository.deleteById(id);
    }
}
