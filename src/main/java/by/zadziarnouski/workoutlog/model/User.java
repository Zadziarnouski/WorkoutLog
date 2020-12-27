package by.zadziarnouski.workoutlog.model;


import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private List<BasicExercise> basicExercises;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)      //Есть другие варианты решить проблему двух коллекций EAGER в одной сущности?
    private List<Measurement> measurements;

    @Column
    private LocalDate birthday;

    @Column
    private float height;

    @Column
    private float weight;

//    @Column
//    private LocalDateTime registrationDate = LocalDateTime.now();

}
