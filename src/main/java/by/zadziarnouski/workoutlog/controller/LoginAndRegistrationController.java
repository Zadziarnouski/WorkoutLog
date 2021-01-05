package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.model.Role;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
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
    

    @Autowired
    public LoginAndRegistrationController(UserService userService){
        this.userService = userService;
        
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("newUser", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute User user, Model model) {
        user.setRole(Role.ROLE_USER);
        userService.saveOrUpdate(user);
        model.addAttribute("registerCompleted","");
        return "login";
    }

}
