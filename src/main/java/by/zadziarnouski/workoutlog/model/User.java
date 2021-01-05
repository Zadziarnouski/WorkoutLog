package by.zadziarnouski.workoutlog.model;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;     //добавить валидацию

    @Column
    private String password;     //добавить валидацию

    @Enumerated
    private Role role;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Enumerated
    private Gender gender;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    //Есть другие варианты решить проблему двух коллекций EAGER в одной сущности?
    private List<Measurement> measurements;

    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    @Column
    private float height;

    @Column
    private float weight;

    @Column
    private LocalDate registrationDate = LocalDate.now();


}
