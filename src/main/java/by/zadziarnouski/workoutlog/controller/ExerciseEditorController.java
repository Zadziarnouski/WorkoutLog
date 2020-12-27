package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.model.BasicExercise;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.BasicExerciseService;
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
@RequestMapping(path = "/exercise-editor")
public class ExerciseEditorController {

    private final BasicExerciseService basicExerciseService;
    private final UserService userService;
    private User currentUser;

    @Autowired
    public ExerciseEditorController(BasicExerciseService basicExerciseService, UserService userService) {
        this.basicExerciseService = basicExerciseService;
        this.userService = userService;
    }

    @GetMapping
    public String getExerciseEditorPage(Model model) {
        currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("basicExercises", currentUser.getBasicExercises());
        return "exercise-editor";
    }

    @GetMapping("/delete/{id}")
    public String deleteExercise(@PathVariable Long id, Model model) {
        basicExerciseService.delete(id);
        return "redirect:/exercise-editor";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForExercise(@PathVariable Long id, Model model) {
        model.addAttribute("basicExercise", basicExerciseService.findById(id));
        return "create-update-exercise";
    }

    @GetMapping("/create")
    public String getCreatePageForExercise(Model model) {
        BasicExercise newBasicExercise = new BasicExercise();
        newBasicExercise.setUser(currentUser);
        model.addAttribute("basicExercise",basicExerciseService.saveOrUpdate(newBasicExercise));
        return "create-update-exercise";
    }

    @PostMapping("/create-update")
    public String createUpdateExercise(@ModelAttribute BasicExercise basicExercise, Model model) {
        BasicExercise savedOrUpdated = basicExerciseService.saveOrUpdate(basicExercise);
        model.addAttribute("basisExercise", savedOrUpdated);
        return "result-create-or-update-exercise";
    }

    private UserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //вынести в отдельный метод и из него брать логин искать юзера и перед созданием нового изменения сетить этого юзера
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }
}
