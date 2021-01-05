package by.zadziarnouski.workoutlog.dto;

import by.zadziarnouski.workoutlog.model.Exercise;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WorkoutDTO {
    private Long id;
    private String title;
    private List<Exercise> exercises;
    private String comments;
    private int restBetweenExercise;
    private int rating;
}
