package by.zadziarnouski.workoutlog.model;


import by.zadziarnouski.workoutlog.validation.EnumNamePattern;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @EnumNamePattern(regexp = "ROLE_USER|ROLE_ADMIN")
    private Role role;

    @NotBlank(message = "Firstname is mandatory")
    private String firstName;

    @NotBlank(message = "Lastname is mandatory")
    private String lastName;

    @EnumNamePattern(regexp = "MALE|FEMALE")
    private Gender gender;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Measurement> measurements;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Workout> workout;

    @Past
    private LocalDate birthday;

    private float height;

    private float weight;

    @PastOrPresent
    private LocalDate registrationDate = LocalDate.now();
}
