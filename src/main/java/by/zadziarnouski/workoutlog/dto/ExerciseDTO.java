package by.zadziarnouski.workoutlog.dto;

import by.zadziarnouski.workoutlog.model.ExerciseType;
import by.zadziarnouski.workoutlog.model.MuscleGroup;
import by.zadziarnouski.workoutlog.model.NecessaryEquipment;
import by.zadziarnouski.workoutlog.model.Workout;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExerciseDTO {
    private Long id;
    private String name;
    private MuscleGroup muscleGroup;
    private ExerciseType exerciseType;
    private int numberOfSets;
    private NecessaryEquipment necessaryEquipment;
    private List<Workout> workouts;
    private List<Integer> set;
    private int restTimeBetweenSets;
    private boolean forAWhile;
    private Long userID;
    private String description;

}
