package by.zadziarnouski.workoutlog.mapper;

import by.zadziarnouski.workoutlog.dto.UserDTO;
import by.zadziarnouski.workoutlog.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class UserMapper {

    private final ModelMapper mapper;

    @Autowired
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public User toEntity(UserDTO userDTO) {
        return Objects.isNull(userDTO) ? null : mapper.map(userDTO, User.class);
    }

    public UserDTO toDTO(User user) {
        return Objects.isNull(user) ? null : mapper.map(user, UserDTO.class);
    }

}
