package by.zadziarnouski.workoutlog.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "exercises")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_of_exercise", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("template")
public class BasicExercise {

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
    private String description;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
