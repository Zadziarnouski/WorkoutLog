package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.mapper.UserMapper;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
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
    public String getMenuPage(HttpSession session, Authentication auth, Model model) {
        if (Objects.nonNull(auth)) {
            User user;
            if (Objects.isNull(session.getAttribute("user"))) {
                UserDetails principal = (UserDetails) auth.getPrincipal();
                user = userService.findByUsername(Objects.requireNonNull(principal.getUsername()));
                session.setAttribute("user", user);
            } else {
                user = (User) session.getAttribute("user");
            }
            model.addAttribute("user", userMapper.toDTO(Objects.requireNonNull(user)));
        }
        return "menu";
    }
}
