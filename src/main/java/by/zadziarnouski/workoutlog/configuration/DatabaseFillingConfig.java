package by.zadziarnouski.workoutlog.configuration;

import by.zadziarnouski.workoutlog.model.*;
import by.zadziarnouski.workoutlog.service.BasicExerciseService;
import by.zadziarnouski.workoutlog.service.MeasurementService;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DatabaseFillingConfig {
    private final BasicExerciseService basicExerciseService;
    private final UserService userService;
    private final MeasurementService measurementService;

    @Autowired
    public DatabaseFillingConfig(BasicExerciseService basicExerciseService, UserService userService, MeasurementService measurementService) {
        this.userService = userService;
        this.basicExerciseService = basicExerciseService;
        this.measurementService = measurementService;
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void addTemplatesApplication() {

        User admin = new User();
        admin.setUsername("tarasss");
        admin.setPassword("1111");
        admin.setRole(Role.ROLE_ADMIN);
        admin.setFirstName("Taras");
        admin.setLastName("Zadziarnouski");
        User save1 = userService.saveOrUpdate(admin);

        BasicExercise templateBasicExercise1 = new BasicExercise();
        templateBasicExercise1.setName("Squats");
        templateBasicExercise1.setMuscleGroup(MuscleGroup.LEGS);
        templateBasicExercise1.setExerciseType(ExerciseType.BASIS);
        templateBasicExercise1.setNecessaryEquipment(NecessaryEquipment.NOEQUIPMENT);
        templateBasicExercise1.setDescription("My exercise");
        templateBasicExercise1.setUser(save1);
        basicExerciseService.saveOrUpdate(templateBasicExercise1);


        BasicExercise templateBasicExercise2 = new BasicExercise();
        templateBasicExercise2.setName("Pull-ups");
        templateBasicExercise2.setMuscleGroup(MuscleGroup.BACK);
        templateBasicExercise2.setExerciseType(ExerciseType.BASIS);
        templateBasicExercise2.setNecessaryEquipment(NecessaryEquipment.HORIZONTALBAR);
        templateBasicExercise2.setDescription("My exercise");
        templateBasicExercise2.setUser(save1);
        basicExerciseService.saveOrUpdate(templateBasicExercise2);

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
            user.setPassword("12345");
            user.setRole(Role.ROLE_USER);
            user.setFirstName("User" + i);
            user.setLastName("User" + i);
            User save = userService.saveOrUpdate(user);
            BasicExercise templateBasicExercise = new BasicExercise();
            templateBasicExercise.setName("Push-ups " + i);
            templateBasicExercise.setMuscleGroup(MuscleGroup.CHEST);
            templateBasicExercise.setExerciseType(ExerciseType.BASIS);
            templateBasicExercise.setNecessaryEquipment(NecessaryEquipment.HORIZONTALBAR);
            templateBasicExercise.setDescription("My templateBasicExercise " + i);
            templateBasicExercise.setUser(save);
            basicExerciseService.saveOrUpdate(templateBasicExercise);
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
