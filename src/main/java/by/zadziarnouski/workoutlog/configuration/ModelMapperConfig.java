package by.zadziarnouski.workoutlog.configuration;

import by.zadziarnouski.workoutlog.mapper.ModelMapperProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    private final ModelMapperProvider modelMapperProvider;

    @Autowired
    public ModelMapperConfig(ModelMapperProvider modelMapperProvider) {
        this.modelMapperProvider = modelMapperProvider;
    }

    @Bean
    public ModelMapper modelMapper() {
        return modelMapperProvider.getModelMapper();
    }
}