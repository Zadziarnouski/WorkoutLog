package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.Measurement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class MeasurementRepositoryTest {
    private final float WEIGHT = 10;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Test
    public void saveMeasurementAndThenGetWeight() {
        Measurement measurement = new Measurement();
        measurement.setWeight(WEIGHT);
        Measurement savedMeasurement = measurementRepository.save(measurement);
        assertNotNull(savedMeasurement.getId());
        assertEquals(10,savedMeasurement.getWeight());
    }


}