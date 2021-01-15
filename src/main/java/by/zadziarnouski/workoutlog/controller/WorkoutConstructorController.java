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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    Workout currentWorkout;


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

        workoutService.findAll().stream().filter(workout -> Objects.isNull(workout.getTitle())).forEach(workout -> workoutService.delete(workout.getId()));

        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        currentUser.getWorkout().add(new Workout());
        User savedUser = userService.saveOrUpdate(currentUser);

//        model.addAttribute("workouts", workoutService.findAll().stream().filter(workout -> Objects.nonNull(workout.getTitle())).map(workoutMapper::toDTO).collect(Collectors.toList())); //достать последние 3 тренировки текущего юзера
//        model.addAttribute("newWorkout", workoutMapper.toDTO(savedUser.getWorkout().get(savedUser.getWorkout().size()-1)));
        return "workout-constructor";
    }


    @GetMapping("/create-new-workout")
    public String getCreateWorkoutPage(Model model) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("newWorkout", workoutMapper.toDTO(currentUser.getWorkout().get(currentUser.getWorkout().size() - 1)));
        return "create-update-workout";
    }

    @GetMapping("/getFormForAddOrUpdateExercise")
    public String addExerciseInWorkout(Model model) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("exercises", exerciseService.findAll().stream().filter(exercise -> exercise.getUser().getId().equals(currentUser.getId())).filter(exercise -> exercise.getNumberOfSets() == 0).map(exerciseMapper::toDTO).collect(Collectors.toList()));
        model.addAttribute("newExercise", exerciseMapper.toDTO(new Exercise()));
        return "create-update-exercise-in-workout";
    }

    @PostMapping("/add-or-update-exercise-in-workout")
    public String addOrUpdateExerciseInWorkout(@ModelAttribute ExerciseDTO exerciseDTO, Model model) {

        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        Workout workout = currentUser.getWorkout().get(currentUser.getWorkout().size() - 1);
        if (Objects.isNull(exerciseDTO.getName())) {
            Exercise newExercise = convectorForNewExercise(exerciseDTO);
            Exercise createdExercise = exerciseService.saveOrUpdate(newExercise);
            workout.getExercises().add(createdExercise);
            workoutService.saveOrUpdate(workout);
            model.addAttribute("workout", workoutMapper.toDTO(workoutService.findById(workout.getId())));
        } else {

            Exercise updateExercise = exerciseService.saveOrUpdate(exerciseMapper.toEntity(exerciseDTO));
            workout.getExercises().add(updateExercise);
            workoutService.saveOrUpdate(workout);
            model.addAttribute("workout", workoutMapper.toDTO(workoutService.findById(workout.getId())));
        }
        return "create-update-workout";
    }

    @GetMapping("/delete/{id}")
    public String deleteExerciseFromWorkout(@PathVariable Long id, Model model) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        Workout currentWorkout = currentUser.getWorkout().get(currentUser.getWorkout().size() - 1);

        for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
            if (currentWorkout.getExercises().get(i).getId().equals(id)) {
                currentWorkout.getExercises().remove(i);
                exerciseService.delete(id);
                workoutService.saveOrUpdate(currentWorkout);
            }
        }
        model.addAttribute("workout", workoutMapper.toDTO(workoutService.saveOrUpdate(currentWorkout)));
        return "create-update-workout";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForExerciseInWorkout(@PathVariable Long id, Model model) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        Workout currentWorkout = currentUser.getWorkout().get(currentUser.getWorkout().size() - 1);

        for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
            if (currentWorkout.getExercises().get(i).getId().equals(id)) {
                model.addAttribute("newExercise", exerciseMapper.toDTO(currentWorkout.getExercises().get(i)));
                currentWorkout.getExercises().remove(i);
                workoutService.saveOrUpdate(currentWorkout);
            }
        }

        return "create-update-exercise-in-workout";
    }

    @GetMapping("/start-training/{id}")
    public String StartTraining(@PathVariable Long id, Model model) {
        Workout workout = workoutService.findById(id);
        workout.getExercises().get(0).setSet(new ArrayList<>(workout.getExercises().get(0).getNumberOfSets()));
        model.addAttribute("workout", workoutMapper.toDTO(workout));
        model.addAttribute("resultOfSet", 0);
        model.addAttribute("restTimeBetweenSets", workout.getExercises().get(0).getRestTimeBetweenSets());


//        for (Exercise ex : workout.getExercises()) {
//            int i = ex.getNumberOfSets();
//            if (ex.getNumberOfSets() > 0) {
//                model.addAttribute("set", 0);
//                ex.setNumberOfSets(i - 1);
//                workoutService.saveOrUpdate(newWorkout);
//                return "workout";
//            }
//        }
        return "workout";
    }


    @PostMapping("/save-result-of-set")
    public String saveResultOfSet(@RequestParam int set, Model model) {

        if (Objects.isNull(currentWorkout)) {
            List<Workout> workouts = workoutService.findAll();
            currentWorkout = workouts.get(workouts.size() - 1);
        }


        for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
            int numberOfSets = currentWorkout.getExercises().get(i).getNumberOfSets();
            if (currentWorkout.getExercises().get(i).getSet().size() == 0) {
                currentWorkout.getExercises().get(i).setSet(new ArrayList<>(currentWorkout.getExercises().get(i).getNumberOfSets()));
            }
            if (numberOfSets > 0) {
                model.addAttribute("resultOfSet", 0);
                model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
                model.addAttribute("restTimeBetweenSets", currentWorkout.getExercises().get(i).getRestTimeBetweenSets());

                if (set == 0) {
                    if (i == (currentWorkout.getExercises().size() - 1) && numberOfSets == 1) {
                        model.addAttribute("finish", "");
                    }
                    return "workout";
                }
                if (i == (currentWorkout.getExercises().size() - 1) && numberOfSets == 1) {
                    model.addAttribute("finish", "");
                }
                currentWorkout.getExercises().get(i).getSet().add(set);
                currentWorkout.getExercises().get(i).setNumberOfSets(numberOfSets - 1);
                currentWorkout = workoutService.saveOrUpdate(currentWorkout);
                break;
            }
        }
        return "countdown-timer";

    }


    @GetMapping("/get-save-workout-form")
    public String getSaveWorkoutForm(Model model) {
        LocalDate now = LocalDate.now();
        currentWorkout.setTitle(now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        return "update-workout-before-saving";
    }

    @PostMapping("/save-workout")
    public String getResultWorkoutPage(@ModelAttribute WorkoutDTO workoutDTO, Model model) {

        currentWorkout.setTitle(workoutDTO.getTitle());
        currentWorkout.setRating(workoutDTO.getRating());
        currentWorkout.setComments(workoutDTO.getComments());


        Workout savedWorkout = workoutService.saveOrUpdate(currentWorkout);
        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        return "result-of-workout";
    }


//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @GetMapping("/workout-start")
//    @ResponseBody
//    public ResponseEntity<?> getCurrentWorkout(){
//
//        WorkoutDTO workoutDTO = workoutMapper.toDTO(newWorkout);
//        return ResponseEntity.ok(workoutDTO);
//    }


    private Exercise convectorForNewExercise(ExerciseDTO exerciseDTO) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());

        Exercise newExercise = new Exercise();
        Exercise byId = exerciseService.findById(exerciseDTO.getId());
        newExercise.setUser(currentUser);
        newExercise.setName(byId.getName());
        newExercise.setExerciseType(byId.getExerciseType());
        newExercise.setNecessaryEquipment(byId.getNecessaryEquipment());
        newExercise.setMuscleGroup(byId.getMuscleGroup());
        newExercise.setDescription(byId.getDescription());
        newExercise.setNumberOfSets(exerciseDTO.getNumberOfSets());
        newExercise.setRestTimeBetweenSets(exerciseDTO.getRestTimeBetweenSets());
        newExercise.setForAWhile(exerciseDTO.isForAWhile());
        return newExercise;

    }

    private UserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //вынести в отдельный метод и из него брать логин искать юзера и перед созданием нового изменения сетить этого юзера
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }

}
