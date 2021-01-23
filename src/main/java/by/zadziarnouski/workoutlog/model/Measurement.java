package by.zadziarnouski.workoutlog.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    public LocalDateTime fixationTime = LocalDateTime.now();

    private String photo;    // Как правильно назвать поле urlToPhotoOnCloudinary?

    private float weight;

    private float height;

    private float neck;

    private float arms;

    private float forearms;

    private float chest;

    private float waist;

    private float buttocks;

    private float thighs;

    private float calves;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
