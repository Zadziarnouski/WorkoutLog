package by.zadziarnouski.workoutlog.dto;

import by.zadziarnouski.workoutlog.model.ExerciseType;
import by.zadziarnouski.workoutlog.model.MuscleGroup;
import by.zadziarnouski.workoutlog.model.NecessaryEquipment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {
    private Long id;
    private String name;
    private MuscleGroup muscleGroup;
    private ExerciseType exerciseType;
    private NecessaryEquipment necessaryEquipment;
    private int numberOfSets;
    private int restTimeBetweenSets;
    private boolean forAWhile;
    private Long userID;
    private Long workoutID;
    private String description;
}
