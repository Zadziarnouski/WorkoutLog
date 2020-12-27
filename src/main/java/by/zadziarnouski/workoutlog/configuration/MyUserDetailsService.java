package by.zadziarnouski.workoutlog.configuration;

import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User byUsername = userService.findByUsername(s);   //можно написать через Optional<User>
        if (Objects.isNull(byUsername)) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(byUsername.getUsername())
                .password(byUsername.getPassword())
                .authorities(byUsername.getRole().toString())
                .build();
    }
}