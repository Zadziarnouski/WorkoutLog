package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.dto.ExerciseDTO;
import by.zadziarnouski.workoutlog.mapper.ExerciseMapper;
import by.zadziarnouski.workoutlog.model.Exercise;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.security.MyUserDetailsService;
import by.zadziarnouski.workoutlog.service.ExerciseService;
import by.zadziarnouski.workoutlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequestMapping(path = "/exercise-editor")
public class ExerciseEditorController {

    private final ExerciseService exerciseService;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseEditorController(ExerciseService exerciseService, ExerciseMapper exerciseMapper) {
        this.exerciseService = exerciseService;
        this.exerciseMapper = exerciseMapper;
    }

    @GetMapping
    public String getExerciseEditorPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        exerciseService.findAll().stream().filter(exercise -> exercise.getName() == null).forEach(exercise -> exerciseService.delete(exercise.getId()));
        model.addAttribute("exercises", exerciseService.findAll().stream()
                .map(exerciseMapper::toDTO)
                .filter(exercise -> exercise.getUserID().equals(user.getId()))
                .collect(Collectors.toList()));
        return "exercise-editor";
    }

    @GetMapping("/delete/{id}")
    public String deleteExercise(@PathVariable Long id, Model model) {
        exerciseService.delete(id);
        return "redirect:/exercise-editor";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForExercise(@PathVariable Long id, Model model) {
        model.addAttribute("exercise", exerciseMapper.toDTO(exerciseService.findById(id)));
        return "create-update-exercise";
    }

    @GetMapping("/create")
    public String getCreatePageForExercise(Model model) {
        Exercise exercise = exerciseService.saveOrUpdate(new Exercise());
        model.addAttribute("exercise", exerciseMapper.toDTO(exercise));
        return "create-update-exercise";
    }

    @PostMapping("/create-update")
    public String createUpdateExercise(@ModelAttribute ExerciseDTO exerciseDTO, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        exerciseDTO.setUserID(user.getId());
        Exercise exercise = exerciseService.saveOrUpdate(exerciseMapper.toEntity(exerciseDTO));
        model.addAttribute("exercise", exerciseMapper.toDTO(exercise));
        return "result-create-or-update-exercise";
    }
}
