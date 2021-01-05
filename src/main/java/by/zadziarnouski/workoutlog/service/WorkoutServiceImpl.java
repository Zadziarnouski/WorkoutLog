package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Workout;
import by.zadziarnouski.workoutlog.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Workout saveOrUpdate(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public List<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @Override
    public Workout findByTitle(String title) {
        return workoutRepository.findByTitle(title);
    }

    @Override
    public Workout findById(long id) {
        Optional<Workout> byId = workoutRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new IllegalArgumentException("Workout with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        workoutRepository.deleteById(id);
    }
}
