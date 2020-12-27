package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.repository.MeasurementRepository;
import by.zadziarnouski.workoutlog.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public Measurement saveOrUpdate(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    @Override
    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Override
    public Measurement findById(long id) {
        Optional<Measurement> byId = measurementRepository.findById(id);
        if(byId.isPresent()){
           return byId.get();
        } else {
            throw new IllegalArgumentException("measurement with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        measurementRepository.deleteById(id);
    }
}
