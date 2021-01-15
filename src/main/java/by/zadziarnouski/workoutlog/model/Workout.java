package by.zadziarnouski.workoutlog.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "exercises_in_workout",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "workout_id"))
    private List<Exercise> exercises = new ArrayList<>();

    @Column
    private String comments;

    @Column
    private int restBetweenExercise;

    @Column
    private int rating;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
