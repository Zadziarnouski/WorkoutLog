package by.zadziarnouski.workoutlog.service;


import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveOrUpdate(User user) {
        logger.trace("User with ID=" + user.getId() + " has been saved or updated");
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllWithTheUserRole() {
        return userRepository.findAllWithTheUserRole();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
           logger.warn("User with such id=" + id + " does not exist");
            throw new IllegalArgumentException("User with such id=" + id + " does not exist");
        }
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
        logger.trace("User with ID=" + id + " has been deleted");
    }
}
