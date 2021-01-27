package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.UserDTO;
import by.zadziarnouski.workoutlog.model.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class UserMapper {

    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User toEntity(UserDTO userDTO) {
        if (Objects.isNull(userDTO)) {
            logger.warn("UserDTO is null");
            return null;
        } else {
            logger.trace("UserDTO with ID=" + userDTO.getId() + " has been converted to Entity");
            return modelMapper.map(userDTO, User.class);
        }
    }

    public UserDTO toDTO(User user) {
        if (Objects.isNull(user)) {
            logger.warn("User is null");
            return null;
        } else {
            logger.trace("User with ID=" + user.getId() + " has been converted to DTO object");
            return modelMapper.map(user, UserDTO.class);
        }
    }

}
