package by.zadziarnouski.workoutlog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @OneToMany(mappedBy = "workout",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Exercise> exercises;

    @Column
    private String comments;

    @Column
    private int restBetweenExercise;

    @Column
    private int rating;
}
