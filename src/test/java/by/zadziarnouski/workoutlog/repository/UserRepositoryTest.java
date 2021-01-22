package by.zadziarnouski.workoutlog.repository;

import by.zadziarnouski.workoutlog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class UserRepositoryTest {
    private final String USERNAME = "Username";
    private final String PASSWORD = "Password";
    private final String LASTNAME = "Lastname";
    private final String FIRSTNAME = "Firstname";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserAndThenGetUsername() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setLastName(LASTNAME);
        user.setFirstName(FIRSTNAME);
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertEquals("Username",savedUser.getUsername());
    }

}