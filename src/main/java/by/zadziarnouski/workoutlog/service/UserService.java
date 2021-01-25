package by.zadziarnouski.workoutlog.service;


import by.zadziarnouski.workoutlog.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User saveOrUpdate(User user);

    List<User> findAll();

    List<User> findAllWithTheUserRole();

    User findById(long id);

    User findByUsername(String username);

    void delete(long id);

}
