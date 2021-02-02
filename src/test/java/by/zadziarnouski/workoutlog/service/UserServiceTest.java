package by.zadziarnouski.workoutlog.service;

import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class UserServiceTest {
    private final Long ID = 10L;
    private final String USERNAME = "Username";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveOrUpdate() {
        when(userRepository.save(any(User.class))).thenReturn(new User());
        User user = new User();
        User savedOrUpdatedUser = userService.saveOrUpdate(user);
        assertNotNull(savedOrUpdatedUser);
    }

    @Test
    void findAll() {
        List<User> users = new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);
        List<User> findAll = userService.findAll();
        assertEquals(3,findAll.size());
    }

    @Test
    void findById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(new User()));
        Optional<User> byId = Optional.of(userService.findById(ID));
        assertNotNull(byId.get());
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(new User());
        User byUsername = userService.findByUsername(USERNAME);
        assertNotNull(byUsername);
    }

    @Test
    void delete() {
        doAnswer(new Answer<Void>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(userRepository).delete(any(User.class));

        userService.delete(ID);
    }
}