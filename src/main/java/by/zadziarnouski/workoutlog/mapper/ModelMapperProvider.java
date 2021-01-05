package by.zadziarnouski.workoutlog.mapper;

import org.modelmapper.ModelMapper;

public interface ModelMapperProvider {
    ModelMapper getModelMapper();
}