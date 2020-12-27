package by.zadziarnouski.workoutlog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String comments;
    @Column
    private int restBetweenExercise;

    @Column
    private int rating;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private Set<BasicExercise> basicExercises;

}
