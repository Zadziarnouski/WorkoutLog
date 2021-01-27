package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.ExerciseDTO;
import by.zadziarnouski.workoutlog.model.Exercise;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class ExerciseMapper {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseMapper.class);
    private final ModelMapper modelMapper;

    @Autowired
    public ExerciseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Exercise toEntity(ExerciseDTO exerciseDTO) {
        if (Objects.isNull(exerciseDTO)) {
            logger.warn("ExerciseDTO is null");
            return null;
        } else {
            logger.trace("ExerciseDTO with ID=" + exerciseDTO.getId() + " has been converted to Entity");
            return modelMapper.map(exerciseDTO, Exercise.class);
        }
    }

    public ExerciseDTO toDTO(Exercise exercise) {
        if (Objects.isNull(exercise)) {
            logger.warn("Exercise is null");
            return null;
        } else {
            logger.trace("Exercise with ID=" + exercise.getId() + " has been converted to DTO object");
            return modelMapper.map(exercise, ExerciseDTO.class);
        }
    }
}
