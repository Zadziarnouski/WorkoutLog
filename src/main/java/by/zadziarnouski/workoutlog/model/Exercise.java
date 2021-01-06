package by.zadziarnouski.workoutlog.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "exercises")
public class Exercise {

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Workout workout;

}
