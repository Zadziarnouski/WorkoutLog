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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
        return "workout-constructor";
    }

    @GetMapping("/create-new-workout")
    public String getCreateWorkoutPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        user.getWorkout().add(new Workout());
        user = userService.saveOrUpdate(user);
        Workout workout = user.getWorkout().get(user.getWorkout().size() - 1);
        session.setAttribute("workout", workout);
        model.addAttribute("workout", workoutMapper.toDTO(workout));
        return "create-update-workout";
    }

    @GetMapping("/getFormForAddOrUpdateExercise")
    public String addExerciseInWorkout(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("exercises", exerciseService.findAll().stream()
                .filter(exercise -> exercise.getUser().getId().equals(user.getId()))
                .filter(exercise -> exercise.getNumberOfSets() == 0)
                .map(exerciseMapper::toDTO).collect(Collectors.toList()));
        model.addAttribute("newExercise", exerciseMapper.toDTO(new Exercise()));
        return "create-update-exercise-in-workout";
    }

    @PostMapping("/add-or-update-exercise-in-workout")
    public String addOrUpdateExerciseInWorkout(@ModelAttribute ExerciseDTO exerciseDTO, HttpSession session, Model model) {
        Workout workout = (Workout) session.getAttribute("workout");
        User user = (User) session.getAttribute("user");

        if (Objects.isNull(exerciseDTO.getName())) {
            Exercise newExercise = convectorForNewExercise(exerciseDTO);
            newExercise.setUser(user);
            Exercise createdExercise = exerciseService.saveOrUpdate(newExercise);
            workout.getExercises().add(createdExercise);
        } else {
            Optional<Exercise> first = workout.getExercises().stream()
                    .filter(exercise -> exercise.getId().equals(exerciseDTO.getId())).findFirst();
            first.ifPresent(exercise -> exercise.setNumberOfSets(exerciseDTO.getNumberOfSets()));
            first.ifPresent(exercise -> exercise.setRestTimeBetweenSets(exerciseDTO.getRestTimeBetweenSets()));
        }
        model.addAttribute("workout", workoutMapper.toDTO(workout));
        return "create-update-workout";
    }

    @GetMapping("/delete/{id}")
    public String deleteExerciseFromWorkout(@PathVariable Long id, HttpSession session, Model model) {
        Workout workout = (Workout) session.getAttribute("workout");
        workout.getExercises().removeIf(exercise -> exercise.getId().equals(id));
        exerciseService.delete(id);
        model.addAttribute("workout", workoutMapper.toDTO(workout));
        return "create-update-workout";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForExerciseInWorkout(@PathVariable Long id, HttpSession session, Model model) {
        Workout workout = (Workout) session.getAttribute("workout");
        Optional<ExerciseDTO> exerciseDTO = workout.getExercises().stream()
                .filter(exercise -> exercise.getId().equals(id))
                .map(exerciseMapper::toDTO)
                .findFirst();
        exerciseDTO.ifPresent(exercise -> model.addAttribute("newExercise", exercise));
        return "create-update-exercise-in-workout";
    }

    @GetMapping("/start-training")
    public String StartTraining(HttpSession session, Model model) {
        Workout workout = (Workout) session.getAttribute("workout");
        int numberOfSets = workout.getExercises().get(0).getNumberOfSets();
        workout.setDuration(LocalTime.now());
        workout.getExercises().get(0).setSet(new ArrayList<>(numberOfSets));
        workout.getExercises().get(0).setNumberOfSets(numberOfSets + 1);
        model.addAttribute("workout", workoutMapper.toDTO(workout));
        model.addAttribute("resultOfSet", 0);
        model.addAttribute("restTimeBetweenSets", workout.getExercises().get(0).getRestTimeBetweenSets());
        return "workout";
    }


    @PostMapping("/save-result-of-set")
    public String saveResultOfSet(@RequestParam int set, HttpSession session, Model model) {
        Workout workout = (Workout) session.getAttribute("workout");
        for (int i = 0; i < workout.getExercises().size(); i++) {
            int numberOfSets = workout.getExercises().get(i).getNumberOfSets();
            if (workout.getExercises().get(i).getSet().size() == 0) {
                workout.getExercises().get(i).setSet(new ArrayList<>(workout.getExercises().get(i).getNumberOfSets()));
            }
            if (numberOfSets > 0) {
                model.addAttribute("resultOfSet", 0);
                model.addAttribute("workout", workoutMapper.toDTO(workout));
                model.addAttribute("restTimeBetweenSets", workout.getExercises().get(i).getRestTimeBetweenSets());
                if (set == 0) {
                    if (i == (workout.getExercises().size() - 1) && numberOfSets == 1) {
                        model.addAttribute("finish", "");
                    }
                    return "workout";
                }
                if (i == (workout.getExercises().size() - 1) && numberOfSets == 1) {
                    model.addAttribute("finish", "");
                }
                workout.getExercises().get(i).getSet().add(set);
                workout.getExercises().get(i).setNumberOfSets(numberOfSets - 1);
                break;
            }
        }
        return "countdown-timer";
    }


    @GetMapping("/get-save-workout-form")
    public String getSaveWorkoutForm(HttpSession session, Model model) {
        Workout workout = (Workout) session.getAttribute("workout");
        LocalTime finish = LocalTime.now();
        workout.setDuration(finish
                .minusHours(workout.getDuration().getHour())
                .minusMinutes(workout.getDuration().getMinute())
                .minusSeconds(workout.getDuration().getSecond()));
        workout.setTitle(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        model.addAttribute("workout", workoutMapper.toDTO(workout));
        return "update-workout-before-saving";
    }

    @PostMapping("/save-workout")
    public String getResultWorkoutPage(@ModelAttribute WorkoutDTO workoutDTO, HttpSession session, Model model) {
        Workout workout = (Workout) session.getAttribute("workout");
        User user = (User) session.getAttribute("user");
        workout.setUser(user);
        workout.setTitle(workoutDTO.getTitle());
        workout.setRating(workoutDTO.getRating());
        workout.setComments(workoutDTO.getComments());
        model.addAttribute("workout", workoutMapper.toDTO(workout));
        workout.getExercises().forEach(exerciseService::saveOrUpdate);
        workoutService.saveOrUpdate(workout);
        return "result-of-workout";
    }

    private Exercise convectorForNewExercise(ExerciseDTO exerciseDTO) {
        Exercise newExercise = new Exercise();
        Exercise byId = exerciseService.findById(exerciseDTO.getId());
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

}
