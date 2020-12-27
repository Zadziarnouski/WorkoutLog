package by.zadziarnouski.workoutlog.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
@DiscriminatorValue("timed_exercise")
public class TimedExercise extends BasicExercise {
    @Column
    private int numberOfSets;
    @Column
    private int restTimeBetweenSets;
//    @ElementCollection
//    private List<Integer> leadTime;

}
