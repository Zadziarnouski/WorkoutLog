package by.zadziarnouski.workoutlog.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;     //Нужны ещё какие-нибудь настройки связи?

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

//    @Column
//    public LocalDateTime fixationTime = LocalDateTime.now();     //передать в DTO и сделать сортировку по дате в журнале

}
