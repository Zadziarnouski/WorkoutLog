package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.WorkoutDTO;
import by.zadziarnouski.workoutlog.model.Workout;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class WorkoutMapper {
    private final ModelMapper mapper;

    @Autowired
    public WorkoutMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Workout toEntity(WorkoutDTO workoutDTO) {
        return Objects.isNull(workoutDTO) ? null : mapper.map(workoutDTO, Workout.class);
    }

    public WorkoutDTO toDTO(Workout workout) {
        return Objects.isNull(workout) ? null : mapper.map(workout, WorkoutDTO.class);
    }

}
