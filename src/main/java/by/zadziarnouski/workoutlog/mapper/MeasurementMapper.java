package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.model.Measurement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class MeasurementMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Measurement toEntity(MeasurementDTO measurementDTO) {
        return Objects.isNull(measurementDTO) ? null : modelMapper.map(measurementDTO, Measurement.class);
    }

    public MeasurementDTO toDTO(Measurement measurement) {
        return Objects.isNull(measurement) ? null : modelMapper.map(measurement, MeasurementDTO.class);
    }

}
