package by.zadziarnouski.workoutlog.controller;


import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.mapper.MeasurementMapper;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/measurement-log")
public class MeasurementLogController {
    private final MeasurementService measurementService;
    private final UserService userService;
    private final MeasurementMapper measurementMapper;


    @Autowired
    public MeasurementLogController(MeasurementService measurementService, UserService userService, MeasurementMapper measurementMapper) {
        this.measurementService = measurementService;
        this.userService = userService;
        this.measurementMapper = measurementMapper;
    }

    @GetMapping
    public String getMeasurementLogPage(Model model) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        measurementService.findAll().stream().filter(measurement -> measurement.getWeight()==0).forEach(measurement -> measurementService.delete(measurement.getId()));
        model.addAttribute("measurements", currentUser.getMeasurements().stream().map(measurementMapper::toDTO).collect(Collectors.toList()));
        return "measurement-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeasurement(@PathVariable Long id, Model model) {
        measurementService.delete(id);
        return "redirect:/measurement-log";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePageForMeasurement(@PathVariable Long id, Model model) {
        model.addAttribute("measurement", measurementMapper.toDTO(measurementService.findById(id)));
        return "create-update-measurement";
    }

    @GetMapping("/create")
    public String getCreatePageForMeasurement(Model model) {
        Measurement newMeasurement = new Measurement();
        Measurement measurement = measurementService.saveOrUpdate(newMeasurement);
        model.addAttribute("measurement", measurementMapper.toDTO(measurement));
        return "create-update-measurement";
    }

    @PostMapping("/create-update")
    public String createUpdateMeasurement(@ModelAttribute MeasurementDTO measurementDTO, Model model) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        measurementDTO.setUserID(currentUser.getId());
        Measurement measurement = measurementService.saveOrUpdate(measurementMapper.toEntity(measurementDTO));
        model.addAttribute("measurement", measurementMapper.toDTO(measurement));
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
