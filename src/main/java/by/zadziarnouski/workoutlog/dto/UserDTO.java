package by.zadziarnouski.workoutlog.dto;

import by.zadziarnouski.workoutlog.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Role role;
    private List<ExerciseDTO> exercises;
    private List<MeasurementDTO> measurements;

}
