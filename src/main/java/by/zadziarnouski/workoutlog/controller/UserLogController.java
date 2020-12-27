package by.zadziarnouski.workoutlog.controller;

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

    @Autowired
    public UserLogController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserLogPage(Model model) {
        List<User> role_user = userService.findAll().stream()
                .filter(user -> user.getRole().toString().equals("ROLE_USER"))
                .collect(Collectors.toList());
        model.addAttribute("users", role_user);
        return "user-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser (@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/user-log";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForUser(@PathVariable Long id, Model model){
        model.addAttribute("user",userService.findById(id));
        return "create-update-user";
    }

    @GetMapping("/create")
    public String getCreatePageForUser(Model model) {
        model.addAttribute("user", new User());
        return "create-update-user";
    }

    @PostMapping("/create-update")
    public String createUpdateUser(@ModelAttribute User user, Model model){
        user.setRole(Role.ROLE_USER);
        model.addAttribute("user",userService.saveOrUpdate(user));
        return "result-create-or-update-user";
    }

}
