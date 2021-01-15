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


    public ModelMapperProviderImpl(UserService userService) {
        this.userService = userService;
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
                });

        modelMapper.typeMap(ExerciseDTO.class, Exercise.class)
                .addMappings(mapper -> mapper.using(longUserConverter()).map(ExerciseDTO::getUserID, Exercise::setUser));
        return modelMapper;
    }


    private Converter<Long, User> longUserConverter() {
        return context -> userService.findById(context.getSource());
    }


}
