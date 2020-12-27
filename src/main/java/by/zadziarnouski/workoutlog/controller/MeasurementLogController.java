package by.zadziarnouski.workoutlog.controller;


import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.MeasurementService;
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
@RequestMapping(path = "/measurement-log")
public class MeasurementLogController {
    private final MeasurementService measurementService;
    private final UserService userService;
    private User currentUser;

    @Autowired
    public MeasurementLogController(MeasurementService measurementService, UserService userService) {
        this.measurementService = measurementService;
        this.userService = userService;
    }

    @GetMapping
    public String getMeasurementLogPage(Model model) {
        currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("measurements", currentUser.getMeasurements());
        return "measurement-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeasurement(@PathVariable Long id, Model model) {
        measurementService.delete(id);
        return "redirect:/measurement-log";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForMeasurement(@PathVariable Long id, Model model) {
        model.addAttribute("measurement", measurementService.findById(id));
        return "create-update-measurement";
    }

    @GetMapping("/create")
    public String getCreatePageForMeasurement(Model model) {
        Measurement newMeasurement = new Measurement();
        newMeasurement.setUser(currentUser);
        model.addAttribute("measurement", newMeasurement);
        return "create-update-measurement";
    }

    @PostMapping("/create-update")
    public String createUpdateMeasurement(@ModelAttribute Measurement measurement, Model model) {
        Measurement savedOrUpdated = measurementService.saveOrUpdate(measurement);
        model.addAttribute("measurement",savedOrUpdated);
        return "result-create-or-update-measurement";
    }

    private UserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //вынести в отдельный метод и из него брать логин искать юзера и перед созданием нового изменения сетить этого юзера
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }
}
