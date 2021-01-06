package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.ExerciseDTO;
import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.dto.WorkoutDTO;
import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.model.Workout;
import by.zadziarnouski.workoutlog.service.ExerciseService;
import by.zadziarnouski.workoutlog.service.UserService;
import by.zadziarnouski.workoutlog.service.WorkoutService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Objects;
import java.util.Optional;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class ModelMapperProviderImpl implements ModelMapperProvider {

    private ModelMapper modelMapper;

    private final UserService userService;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;

    public ModelMapperProviderImpl(UserService userService, ExerciseService exerciseService, WorkoutService workoutService) {
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
    }

    @Override
    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @PostConstruct
    private ModelMapper createModelMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        modelMapper.typeMap(Measurement.class, MeasurementDTO.class)
                .addMappings(mapper -> mapper.map(measurement -> measurement.getUser().getId(), MeasurementDTO::setUserID));
        modelMapper.typeMap(MeasurementDTO.class, Measurement.class)
                .addMappings(mapper -> mapper.using(longUserConverter()).map(MeasurementDTO::getUserID, Measurement::setUser));

        modelMapper.createTypeMap(Exercise.class, ExerciseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(exercise -> exercise.getUser().getId(), ExerciseDTO::setUserID);
                    mapper.map(exercise -> exercise.getWorkout().getId(), ExerciseDTO::setWorkoutID); //?
                });

        modelMapper.createTypeMap(ExerciseDTO.class, Exercise.class)
                .addMappings(mapper -> mapper.skip(Exercise::setWorkout)).setPostConverter(toEntityConverter());

        modelMapper.typeMap(ExerciseDTO.class, Exercise.class)
                .addMappings(mapper -> mapper.using(longUserConverter()).map(ExerciseDTO::getUserID, Exercise::setUser));
        return modelMapper;
    }

    private Converter<ExerciseDTO, Exercise> toEntityConverter() {
        return context -> {
            ExerciseDTO source = context.getSource();
            Exercise destination = context.getDestination();
            mapSpecificFields(source,destination);
            return context.getDestination();
        };
    }


    private Converter<Long, User> longUserConverter() {
        return context -> userService.findById(context.getSource());
    }

    void mapSpecificFields(ExerciseDTO source, Exercise destination) {
        if(Objects.nonNull(source.getWorkoutID())){
            destination.setWorkout(workoutService.findById(source.getWorkoutID()));
        }
    }



}
