package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.model.Measurement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class MeasurementMapper {
    private static final Logger logger = LoggerFactory.getLogger(MeasurementMapper.class);
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Measurement toEntity(MeasurementDTO measurementDTO) {
        if (Objects.isNull(measurementDTO)) {
            logger.warn("MeasurementDTO is null");
            return null;
        } else {
            logger.trace("MeasurementDTO with ID=" + measurementDTO.getId() + " has been converted to Entity");
            return modelMapper.map(measurementDTO, Measurement.class);
        }
    }

    public MeasurementDTO toDTO(Measurement measurement) {
        if (Objects.isNull(measurement)) {
            logger.warn("Measurement is null");
            return null;
        } else {
            logger.trace("Measurement with ID=" + measurement.getId() + " has been converted to DTO object");
            return modelMapper.map(measurement, MeasurementDTO.class);
        }
    }
}