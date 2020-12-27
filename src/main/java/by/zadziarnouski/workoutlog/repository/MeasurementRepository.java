package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}
