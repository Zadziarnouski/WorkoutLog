package by.zadziarnouski.workoutlog.model;


import by.zadziarnouski.workoutlog.validation.EnumNamePattern;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "exercises")
public class Exercise  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @NotBlank(message = "Name is mandatory")
    private String name;

    @EnumNamePattern(regexp = "CHEST|BACK|ARMS|SHOULDERS|LEGS|ABS|CALVES")
    private MuscleGroup muscleGroup;

    @EnumNamePattern(regexp = "BASIS|ISOLATION|CARDIO|STATIC")
    private ExerciseType exerciseType;

    @EnumNamePattern(regexp = "NOEQUIPMENT|ELASTICBAND|HORIZONTALBAR|KETTLEBELL|MAT")
    private NecessaryEquipment necessaryEquipment;

    private int numberOfSets;

    private int restTimeBetweenSets;

    private boolean forAWhile;

    private String description;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ToString.Exclude
    @ManyToMany(mappedBy = "exercises"
            ,fetch = FetchType.EAGER
            ,cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    private List<@PositiveOrZero Integer> set = new ArrayList<>();
}
