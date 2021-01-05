package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.ExerciseDTO;
import by.zadziarnouski.workoutlog.model.Exercise;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class ExerciseMapper {

    private final ModelMapper mapper;

    @Autowired
    public ExerciseMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Exercise toEntity(ExerciseDTO exerciseDTO) {
        return Objects.isNull(exerciseDTO) ? null : mapper.map(exerciseDTO, Exercise.class);
    }

    public ExerciseDTO toDTO(Exercise exercise) {
        return Objects.isNull(exercise) ? null : mapper.map(exercise, ExerciseDTO.class);
    }

}
