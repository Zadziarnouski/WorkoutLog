package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.repository.MeasurementRepository;
import by.zadziarnouski.workoutlog.service.MeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementServiceImpl.class);
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public Measurement saveOrUpdate(Measurement measurement) {
        logger.trace("Measurement with ID=" + measurement.getId() + "has been saved or updated");
        return measurementRepository.save(measurement);
    }

    @Override
    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Override
    public Measurement findById(long id) {
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            logger.warn("Measurement with such id=" + id + "does not exist");
            throw new IllegalArgumentException("measurement with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        measurementRepository.deleteById(id);
        logger.trace("Measurement with ID=" + id + "has been deleted");
    }
}
