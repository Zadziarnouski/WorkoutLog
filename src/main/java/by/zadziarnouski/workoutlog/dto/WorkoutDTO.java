package by.zadziarnouski.workoutlog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WorkoutDTO {
    private Long id;
    private String title;
    private List<ExerciseDTO> exercises;
    private String comments;
    private int restBetweenExercise;
    private int rating;
}
