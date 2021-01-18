package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.mapper.UserMapper;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.model.Workout;
import by.zadziarnouski.workoutlog.service.UserService;
import by.zadziarnouski.workoutlog.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(path = {"/", "/menu"})
public class MenuController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public MenuController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String getMenuPage(Model model) {
        model.addAttribute("myUser", userMapper.toDTO(userService.findByUsername(Objects.requireNonNull(getPrincipal()))));
        return "menu";
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
