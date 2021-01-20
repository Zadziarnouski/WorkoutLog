package by.zadziarnouski.workoutlog.dto;

import by.zadziarnouski.workoutlog.model.Gender;
import by.zadziarnouski.workoutlog.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Role role;
    private String birthday;
    private float height;
    private float weight;

}
