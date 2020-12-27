package by.zadziarnouski.workoutlog.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity

@DiscriminatorValue("repetition_exercise")
public class RepetitionExercise extends BasicExercise {
    @Column
    private int numberOfSets;
    @Column
    private int restTimeBetweenSets;
//    @ElementCollection
//    private List<Integer> numberOfRepetition;
}
