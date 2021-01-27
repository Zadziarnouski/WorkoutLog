package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.ExerciseDTO;
import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(toStringDate);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        modelMapper.createTypeMap(String.class, LocalTime.class);
        modelMapper.addConverter(toStringTime);
        modelMapper.getTypeMap(String.class, LocalTime.class).setProvider(localTimeProvider);


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

    Provider<LocalDate> localDateProvider = new AbstractProvider<>() {
        @Override
        public LocalDate get() {
            return LocalDate.now();
        }
    };

    Provider<LocalTime> localTimeProvider = new AbstractProvider<>() {
        @Override
        public LocalTime get() {
            return LocalTime.now();
        }
    };

    Converter<String, LocalTime> toStringTime = new AbstractConverter<>() {
        @Override
        protected LocalTime convert(String source) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm:ss");
            return LocalTime.parse(source, format);
        }
    };

    Converter<String, LocalDate> toStringDate = new AbstractConverter<>() {
        @Override
        protected LocalDate convert(String source) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(source, format);
        }
    };


}
