package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.model.Role;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping(path = "/personal-profile")
public class PersonalProfileController {
    private final UserService userService;

    @Autowired
    public PersonalProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getPersonalProfilePage(Model model) {
        User user = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("user", user);
        return "personal-profile";
    }


    @PostMapping("/update")
    public String UpdateProfile(@ModelAttribute User user, Model model) {
        User updated = userService.saveOrUpdate(user);
        return "redirect:/personal-profile";
    }

    @GetMapping("/get-update-form")
    public String getUpdateForm(Model model) {
        User user = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("user", user);
        return "update-personal-profile";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        userService.delete(id);
        return "redirect:/user-log";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "create-update-user";
    }

    @GetMapping("/create")
    public String getCreatePageForUser(Model model) {
        model.addAttribute("user", new User());
        return "create-update-user";
    }

    @PostMapping("/create-update")
    public String createUpdateUser(@ModelAttribute User user, Model model) {
        user.setRole(Role.ROLE_USER);
        model.addAttribute("user", userService.saveOrUpdate(user));
        return "result-create-or-update-user";
    }

    private UserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //вынести в отдельный метод и из него брать логин искать юзера и перед созданием нового изменения сетить этого юзера
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }
}
