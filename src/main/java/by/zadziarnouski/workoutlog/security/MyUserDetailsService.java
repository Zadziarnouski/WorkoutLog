package by.zadziarnouski.workoutlog.security;

import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userRepository.findByUsername(username);
        if (Objects.isNull(byUsername)) {
            logger.warn("User with such USERNAME=" + username + "does not exist");
            throw new UsernameNotFoundException("User with such USERNAME=" + username + "does not exist");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(byUsername.getUsername())
                .password(byUsername.getPassword())
                .authorities(byUsername.getRole().toString())
                .build();
    }

}