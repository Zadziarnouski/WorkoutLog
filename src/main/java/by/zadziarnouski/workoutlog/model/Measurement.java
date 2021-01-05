package by.zadziarnouski.workoutlog.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    public LocalDateTime fixationTime = LocalDateTime.now();

    @Column
    private float weight;

    @Column
    private float height;

    @Column
    private float neck;

    @Column
    private float arms;

    @Column
    private float forearms;

    @Column
    private float chest;

    @Column
    private float waist;

    @Column
    private float buttocks;

    @Column
    private float thighs;

    @Column
    private float calves;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private User user;



}
