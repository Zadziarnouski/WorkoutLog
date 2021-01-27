package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.dto.UserDTO;
import by.zadziarnouski.workoutlog.mapper.UserMapper;
import by.zadziarnouski.workoutlog.model.Role;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class LoginAndRegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(LoginAndRegistrationController.class);

    @Autowired
    public LoginAndRegistrationController(UserService userService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("newUser", new UserDTO());
        logger.info("User went to registration page");
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute UserDTO userDTO, Model model) {
        userDTO.setRole(Role.ROLE_USER);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userService.saveOrUpdate(userMapper.toEntity(userDTO));
        model.addAttribute("registerCompleted", "");
        logger.info("New user registered with ID=" + user.getId());
        return "login";
    }

}
