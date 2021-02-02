package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.repository.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class MeasurementServiceTest {
    private final float WEIGHT = 10;
    private final Long ID = 10L;

    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private MeasurementServiceImpl measurementService;

    @Test
    void saveOrUpdate() {
        when(measurementRepository.save(any(Measurement.class))).thenReturn(new Measurement());
        Measurement measurement = new Measurement();
        Measurement savedOrUpdatedMeasurement = measurementService.saveOrUpdate(measurement);
        assertNotNull(savedOrUpdatedMeasurement);
    }

    @Test
    void findAll() {
        List<Measurement> list= new ArrayList<Measurement>();
        list.add(new Measurement());
        list.add(new Measurement());
        list.add(new Measurement());
        when(measurementRepository.findAll()).thenReturn(list);
        List<Measurement> findAll = measurementService.findAll();
        assertEquals(3, findAll.size());
    }

    @Test
    void findById() {
        when(measurementRepository.findById(any(Long.class))).thenReturn(Optional.of(new Measurement()));
        Optional<Measurement> byId = Optional.of(measurementService.findById(ID));
        assertNotNull(byId.get());
    }

    @Test
    void delete() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(measurementRepository).delete(any(Measurement.class));
        measurementService.delete(ID);
    }
}