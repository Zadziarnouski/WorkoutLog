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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path = "/workout-constructor")
public class WorkoutConstructorController {
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;
    private final UserService userService;
    private final WorkoutMapper workoutMapper;
    private final ExerciseMapper exerciseMapper;
    User currentUser;
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
    public String getWorkoutConstructorPage() {
        workoutService.findAll().stream().filter(workout -> workout.getTitle() == null).forEach(workout -> workoutService.delete(workout.getId()));
        currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        return "workout-constructor";
    }

    @GetMapping("/create-new-workout")
    public String getCreateWorkoutPage(Model model) {
        currentUser.getWorkout().add(new Workout());
        currentUser = userService.saveOrUpdate(currentUser);
        currentWorkout = currentUser.getWorkout().get(currentUser.getWorkout().size() - 1);


        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        return "create-update-workout";
    }

    @GetMapping("/getFormForAddOrUpdateExercise")
    public String addExerciseInWorkout(Model model) {
        model.addAttribute("exercises", exerciseService.findAll().stream().filter(exercise -> exercise.getUser().getId().equals(currentUser.getId())).filter(exercise -> exercise.getNumberOfSets() == 0).map(exerciseMapper::toDTO).collect(Collectors.toList()));
        model.addAttribute("newExercise", exerciseMapper.toDTO(new Exercise()));
        return "create-update-exercise-in-workout";
    }

    @PostMapping("/add-or-update-exercise-in-workout")
    public String addOrUpdateExerciseInWorkout(@ModelAttribute ExerciseDTO exerciseDTO, Model model) {
        currentWorkout.setUser(currentUser);
        if (Objects.isNull(exerciseDTO.getName())) {
            Exercise newExercise = convectorForNewExercise(exerciseDTO);
            Exercise createdExercise = exerciseService.saveOrUpdate(newExercise);
            currentWorkout.getExercises().add(createdExercise);

        } else {
            exerciseService.saveOrUpdate(exerciseMapper.toEntity(exerciseDTO));
            for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
                if (currentWorkout.getExercises().get(0).getId().equals(exerciseDTO.getId())) {
                    currentWorkout.getExercises().get(i).setNumberOfSets(exerciseDTO.getNumberOfSets());
                    currentWorkout.getExercises().get(i).setRestTimeBetweenSets(exerciseDTO.getRestTimeBetweenSets());
                }
            }
        }
        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        return "create-update-workout";
    }

    @GetMapping("/delete/{id}")
    public String deleteExerciseFromWorkout(@PathVariable Long id, Model model) {
        for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
            if (currentWorkout.getExercises().get(i).getId().equals(id)) {
                currentWorkout.getExercises().remove(i);
                exerciseService.delete(id);
            }
        }
        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        return "create-update-workout";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForExerciseInWorkout(@PathVariable Long id, Model model) {
        for (int i = 0; i < currentWorkout.getExercises().size(); i++) {
            if (currentWorkout.getExercises().get(i).getId().equals(id)) {
                model.addAttribute("newExercise", exerciseMapper.toDTO(currentWorkout.getExercises().get(i)));
            }
        }

        return "create-update-exercise-in-workout";
    }

    @GetMapping("/start-training/{id}")
    public String StartTraining(@PathVariable Long id, Model model) {
        int numberOfSets = currentWorkout.getExercises().get(0).getNumberOfSets();
        currentWorkout.setDuration(LocalTime.now());
        currentWorkout.getExercises().get(0).setSet(new ArrayList<>(numberOfSets));
        currentWorkout.getExercises().get(0).setNumberOfSets(numberOfSets + 1);
        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        model.addAttribute("resultOfSet", 0);
        model.addAttribute("restTimeBetweenSets", currentWorkout.getExercises().get(0).getRestTimeBetweenSets());

        return "workout";
    }


    @PostMapping("/save-result-of-set")
    public String saveResultOfSet(@RequestParam int set, Model model) {

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

                break;
            }
        }
        return "countdown-timer";

    }


    @GetMapping("/get-save-workout-form")
    public String getSaveWorkoutForm(Model model) {
        LocalTime finish = LocalTime.now();
        currentWorkout.setDuration(finish
                .minusHours(currentWorkout.getDuration().getHour())
                .minusMinutes(currentWorkout.getDuration().getMinute())
                .minusSeconds(currentWorkout.getDuration().getSecond()));
        currentWorkout.setTitle(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        return "update-workout-before-saving";
    }

    @PostMapping("/save-workout")
    public String getResultWorkoutPage(@ModelAttribute WorkoutDTO workoutDTO, Model model) {
        currentWorkout.setTitle(workoutDTO.getTitle());
        currentWorkout.setRating(workoutDTO.getRating());
        currentWorkout.setComments(workoutDTO.getComments());
        model.addAttribute("workout", workoutMapper.toDTO(currentWorkout));
        currentWorkout.getExercises().stream().map(exerciseService::saveOrUpdate);
        workoutService.saveOrUpdate(currentWorkout);
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
