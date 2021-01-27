package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.WorkoutDTO;
import by.zadziarnouski.workoutlog.model.Workout;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class WorkoutMapper {
    private static final Logger logger = LoggerFactory.getLogger(WorkoutMapper.class);
    private final ModelMapper modelMapper;

    @Autowired
    public WorkoutMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Workout toEntity(WorkoutDTO workoutDTO) {
        if (Objects.isNull(workoutDTO)) {
            logger.warn("WorkoutDTO is null");
            return null;
        } else {
            logger.trace("WorkoutDTO with ID=" + workoutDTO.getId() + " has been converted to Entity");
            return modelMapper.map(workoutDTO, Workout.class);
        }
    }

    public WorkoutDTO toDTO(Workout workout) {
        if (Objects.isNull(workout)) {
            logger.warn("Workout is null");
            return null;
        } else {
            logger.trace("Workout with ID=" + workout.getId() + " has been converted to DTO object");
            return modelMapper.map(workout, WorkoutDTO.class);
        }
    }
}
