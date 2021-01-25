package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.mapper.UserMapper;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/user-log")
public class UserLogController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserLogController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getUserLogPage(Model model) {
        model.addAttribute("users", userService.findAllWithTheUserRole().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
        return "user-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/user-log";
    }
}
