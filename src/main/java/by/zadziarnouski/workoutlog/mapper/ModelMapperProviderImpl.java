package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.ExerciseDTO;
import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.ExerciseService;
import by.zadziarnouski.workoutlog.service.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class ModelMapperProviderImpl implements ModelMapperProvider {

    private ModelMapper modelMapper;

    private final UserService userService;
    private final ExerciseService exerciseService;

    public ModelMapperProviderImpl(UserService userService, ExerciseService exerciseService) {
        this.userService = userService;
        this.exerciseService = exerciseService;
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


        modelMapper.typeMap(Exercise.class, ExerciseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(exercise -> exercise.getUser().getId(), ExerciseDTO::setUserID);
                    mapper.map(exercise -> exercise.getWorkout().getId(), ExerciseDTO::setWorkoutID);
                });

        modelMapper.typeMap(ExerciseDTO.class, Exercise.class)
                .addMappings(mapper -> {
                    mapper.using(longExerciseConverter()).map(ExerciseDTO::getUserID, Exercise::setUser);
                    mapper.using(longExerciseConverter()).map(ExerciseDTO::getWorkoutID,Exercise::setWorkout);
                });

//        modelMapper.typeMap(User.class, UserDTO.class).addMappings(mapper -> {
//           mapper.map(user -> user.getRole().getDisplayValue(),UserDTO::setRole);
//        });
//        modelMapper.typeMap(UserDTO.class, User.class).addMappings(mapper -> {
//            mapper.map(user -> Enum.valueOf(Role.class,user.getRole()),User::setRole);
//        });


        return modelMapper;
    }

    private Converter<Long, Exercise> longExerciseConverter() {
        return context -> exerciseService.findById(context.getSource());
    }

    private Converter<Long, User> longUserConverter() {
        return context -> userService.findById(context.getSource());
    }
}
