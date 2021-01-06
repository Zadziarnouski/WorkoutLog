package by.zadziarnouski.workoutlog.configuration;

import by.zadziarnouski.workoutlog.model.*;
import by.zadziarnouski.workoutlog.service.ExerciseService;
import by.zadziarnouski.workoutlog.service.MeasurementService;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
public class DatabaseFillingConfig {
    private final ExerciseService exerciseService;
    private final UserService userService;
    private final MeasurementService measurementService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseFillingConfig(ExerciseService exerciseService, UserService userService, MeasurementService measurementService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.measurementService = measurementService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addTemplatesApplication() {

        User admin = new User();
        admin.setUsername("tarasss");
        admin.setPassword(passwordEncoder.encode("1111"));
        admin.setRole(Role.ROLE_ADMIN);
        admin.setFirstName("Taras");
        admin.setLastName("Zadziarnouski");
        admin.setHeight(181f);
        admin.setGender(Gender.MALE);
        admin.setWeight(73f);
        admin.setBirthday(LocalDate.parse("1994-08-03"));

        User save1 = userService.saveOrUpdate(admin);

        Exercise templateExercise1 = new Exercise();
        templateExercise1.setName("Squats");
        templateExercise1.setMuscleGroup(MuscleGroup.LEGS);
        templateExercise1.setExerciseType(ExerciseType.BASIS);
        templateExercise1.setNecessaryEquipment(NecessaryEquipment.NOEQUIPMENT);
        templateExercise1.setDescription("My exercise");
        templateExercise1.setUser(save1);
        exerciseService.saveOrUpdate(templateExercise1);


        Exercise templateExercise2 = new Exercise();
        templateExercise2.setName("Pull-ups");
        templateExercise2.setMuscleGroup(MuscleGroup.BACK);
        templateExercise2.setExerciseType(ExerciseType.BASIS);
        templateExercise2.setNecessaryEquipment(NecessaryEquipment.HORIZONTALBAR);
        templateExercise2.setDescription("My exercise");
        templateExercise2.setUser(save1);
        exerciseService.saveOrUpdate(templateExercise2);

        Measurement measurement = new Measurement();
        measurement.setWeight(72.5f);
        measurement.setHeight(181f);
        measurement.setNeck(30.5f);
        measurement.setArms(35.5f);
        measurement.setForearms(27.5f);
        measurement.setChest(105.5f);
        measurement.setWaist(40f);
        measurement.setButtocks(70.5f);
        measurement.setThighs(49.5f);
        measurement.setCalves(35f);
        measurement.setUser(save1);
        measurementService.saveOrUpdate(measurement);

        Measurement measurement1 = new Measurement();
        measurement1.setWeight(86.5f);
        measurement1.setHeight(175f);
        measurement1.setNeck(34f);
        measurement1.setArms(40f);
        measurement1.setForearms(30.5f);
        measurement1.setChest(90.5f);
        measurement.setWaist(40f);
        measurement1.setButtocks(90.5f);
        measurement1.setThighs(50.7f);
        measurement1.setCalves(40f);
        measurement1.setUser(save1);
        measurementService.saveOrUpdate(measurement1);


        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setUsername("user" + i);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRole(Role.ROLE_USER);
            user.setFirstName("User" + i);
            user.setLastName("User" + i);
            user.setHeight(175f);
            user.setGender(Gender.FEMALE);
            user.setWeight(80f);
            user.setBirthday(LocalDate.parse("2001-01-01"));
            User save = userService.saveOrUpdate(user);
            Exercise templateExercise = new Exercise();
            templateExercise.setName("Push-ups " + i);
            templateExercise.setMuscleGroup(MuscleGroup.CHEST);
            templateExercise.setExerciseType(ExerciseType.BASIS);
            templateExercise.setNecessaryEquipment(NecessaryEquipment.HORIZONTALBAR);
            templateExercise.setDescription("My templateExercise " + i);
            templateExercise.setUser(save);
            exerciseService.saveOrUpdate(templateExercise);
            Measurement measurement2 = new Measurement();
            measurement2.setWeight(1 + i);
            measurement2.setHeight(1 + i);
            measurement2.setNeck(1 + i);
            measurement2.setArms(1 + i);
            measurement2.setForearms(1 + i);
            measurement2.setChest(1 + i);
            measurement2.setWaist(1 + i);
            measurement2.setButtocks(1 + i);
            measurement2.setThighs(1 + i);
            measurement2.setCalves(1 + i);
            measurement2.setUser(save);
            measurementService.saveOrUpdate(measurement2);
        }


    }

}
