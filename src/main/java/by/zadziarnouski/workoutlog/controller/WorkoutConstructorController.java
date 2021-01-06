package by.zadziarnouski.workoutlog.controller;

import by.zadziarnouski.workoutlog.dto.ExerciseDTO;
import by.zadziarnouski.workoutlog.dto.WorkoutDTO;
import by.zadziarnouski.workoutlog.mapper.ExerciseMapper;
import by.zadziarnouski.workoutlog.mapper.WorkoutMapper;
import by.zadziarnouski.workoutlog.model.*;
import by.zadziarnouski.workoutlog.service.ExerciseService;
import by.zadziarnouski.workoutlog.service.UserService;
import by.zadziarnouski.workoutlog.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path = "/workout-constructor")
public class WorkoutConstructorController {
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;
    private final UserService userService;
    private final WorkoutMapper workoutMapper;
    private final ExerciseMapper exerciseMapper;
    private User currentUser;
    private Workout newWorkout;


    @Autowired
    public WorkoutConstructorController(WorkoutService workoutService, ExerciseService exerciseService, UserService userService, WorkoutMapper workoutMapper, ExerciseMapper exerciseMapper) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.workoutMapper = workoutMapper;
        this.exerciseMapper = exerciseMapper;
    }

    @GetMapping
    public String getWorkoutPage(Model model) {
        currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("workouts", workoutService.findAll().stream().map(workoutMapper::toDTO).collect(Collectors.toList()));
        model.addAttribute("newWorkout", workoutMapper.toDTO(new Workout()));
        return "workout-constructor";
    }

    @PostMapping("/start-training")
    public String StartTraining(@ModelAttribute WorkoutDTO workoutDTO, Model model) {
        model.addAttribute("workout", workoutMapper.toDTO(newWorkout));
        return "workout";
    }

    @GetMapping("/create-new-workout")
    public String createNewWorkout(Model model) {
        newWorkout = new Workout();
        newWorkout.setExercises(new ArrayList<>());
        model.addAttribute("workout", workoutMapper.toDTO(newWorkout));
        model.addAttribute("exercises", exerciseService.findAll().stream().map(exerciseMapper::toDTO).collect(Collectors.toList()));
        return "create-update-workout";
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

    @GetMapping("/getFormForAddOrUpdateExercise")
    public String addExerciseInWorkout(Model model) {
        model.addAttribute("exercises", exerciseService.findAll().stream().map(exerciseMapper::toDTO).collect(Collectors.toList()));
        model.addAttribute("newExercise", exerciseMapper.toDTO(new Exercise()));
        return "create-update-exercise-in-workout";
    }

    @PostMapping("/add-or-update-exercise-in-workout")
    public String addOrUpdateExerciseInWorkout(@ModelAttribute ExerciseDTO exerciseDTO, Model model) {

        Exercise added = exerciseService.saveOrUpdate(new Exercise());
        Exercise byId = exerciseService.findById(exerciseDTO.getId());
        added.setName(byId.getName());
        added.setNumberOfSets(exerciseDTO.getNumberOfSets());
        added.setRestTimeBetweenSets(exerciseDTO.getNumberOfSets());

        newWorkout.getExercises().add(added);

        model.addAttribute("exercises", exerciseService.findAll().stream().map(exerciseMapper::toDTO).collect(Collectors.toList()));
        model.addAttribute("workout", workoutMapper.toDTO(newWorkout));
        return "create-update-workout";
    }


    @GetMapping("/create")
    public String getCreatePageForExercise(Model model) {
        model.addAttribute("exercise", exerciseMapper.toDTO(new Exercise()));
        return "create-update-exercise";
    }

    @PostMapping("/create-update")
    public String createUpdateExercise(@ModelAttribute ExerciseDTO exerciseDTO, Model model) {
        exerciseDTO.setUserID(currentUser.getId());
        Exercise exercise = exerciseService.saveOrUpdate(exerciseMapper.toEntity(exerciseDTO));
        model.addAttribute("exercise",exerciseMapper.toDTO(exercise));
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
