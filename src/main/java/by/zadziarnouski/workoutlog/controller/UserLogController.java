package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.dto.UserDTO;
import by.zadziarnouski.workoutlog.mapper.UserMapper;
import by.zadziarnouski.workoutlog.model.Role;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        List<UserDTO> role_user = userService.findAll().stream()
                .filter(user -> user.getRole().toString().equals("ROLE_USER"))
                .map(userMapper::toDTO).collect(Collectors.toList());
        model.addAttribute("users", role_user);
        return "user-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/user-log";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userMapper.toDTO(userService.findById(id)));
        return "create-update-user";
    }

    @GetMapping("/create")
    public String getCreatePageForUser(Model model) {
        model.addAttribute("user", userMapper.toDTO(new User()));
        return "create-update-user";
    }

    @PostMapping("/create-update")
    public String createUpdateUser(@ModelAttribute UserDTO userDTO, Model model) {
        userDTO.setRole(Role.ROLE_USER);
        User user = userService.saveOrUpdate(userMapper.toEntity(userDTO));
        model.addAttribute("user", userMapper.toDTO(user));
        return "result-create-or-update-user";
    }

}
