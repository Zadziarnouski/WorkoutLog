package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.WorkoutLogApplication;
import by.zadziarnouski.workoutlog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WorkoutLogApplication.class})
class UserRepositoryIntegrationTest {
    private final String USERNAME = "Username";
    private final String PASSWORD = "Password";
    private final String FIRSTNAME = "Firstname";
    private final String LASTNAME = "Lastname";

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setFirstName(FIRSTNAME);
        user.setLastName(LASTNAME);
        userRepository.save(user);
        assertNotNull(userRepository.findByUsername("Username"));
    }
}