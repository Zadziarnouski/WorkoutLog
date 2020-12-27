package by.zadziarnouski.workoutlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/workout-log")
public class WorkoutLogController {

    @GetMapping
    public String getWorkoutLogPage(Model model){
        return "workout-log";
    }
}
