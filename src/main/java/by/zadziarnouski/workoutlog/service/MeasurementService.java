package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Measurement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeasurementService {
    Measurement saveOrUpdate(Measurement measurement);

    List<Measurement> findAll();

    Measurement findById(long id);

    void delete(long id);

}
