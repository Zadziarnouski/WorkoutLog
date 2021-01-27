package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.dto.UserDTO;
import by.zadziarnouski.workoutlog.mapper.UserMapper;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping(path = "/personal-profile")
public class PersonalProfileController {
    private final UserService userService;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(PersonalProfileController.class);


    @Autowired
    public PersonalProfileController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public String getPersonalProfilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", userMapper.toDTO(user));
        return "personal-profile";
    }

    @PostMapping("/update")
    public String UpdateProfile(@ModelAttribute UserDTO userDTO, HttpSession session) {
        User user = userService.saveOrUpdate(userMapper.toEntity(userDTO));
        session.setAttribute("user", user);
        logger.info("User with ID=" +userDTO.getId() + " updated their personal profile");
        return "redirect:/personal-profile";
    }

    @GetMapping("/get-update-form")
    public String getUpdateForm(HttpSession session, Model model) {
        model.addAttribute("user", userMapper.toDTO((User) session.getAttribute("user")));
        return "update-personal-profile";
    }
}
