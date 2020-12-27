package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/")
public class MenuController {
    private final UserService userService;

    @Autowired
    public MenuController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String getMenuPage(Model model) {
        model.addAttribute("myUser",userService.findByUsername(getPrincipal()));
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
