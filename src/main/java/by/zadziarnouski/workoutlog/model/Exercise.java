package by.zadziarnouski.workoutlog.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "exercises")
public class Exercise  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Enumerated
    private MuscleGroup muscleGroup;

    @Enumerated
    private ExerciseType exerciseType;

    @Enumerated
    private NecessaryEquipment necessaryEquipment;

    @Column
    private int numberOfSets;

    @Column
    private int restTimeBetweenSets;

    @Column
    private boolean forAWhile;

    @Column
    private String description;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ToString.Exclude
    @ManyToMany(mappedBy = "exercises",fetch = FetchType.EAGER)
    private List<Workout> workouts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> set = new ArrayList<>();
}
