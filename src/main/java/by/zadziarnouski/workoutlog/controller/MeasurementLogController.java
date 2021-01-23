package by.zadziarnouski.workoutlog.controller;


import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.mapper.MeasurementMapper;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.MeasurementService;
import by.zadziarnouski.workoutlog.service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/measurement-log")
public class MeasurementLogController {
    private final MeasurementService measurementService;
    private final UserService userService;
    private final MeasurementMapper measurementMapper;
    private final Cloudinary cloudinary;
    private Measurement newMeasurement;


    @Autowired
    public MeasurementLogController(MeasurementService measurementService, UserService userService, MeasurementMapper measurementMapper, Cloudinary cloudinary) {
        this.measurementService = measurementService;
        this.userService = userService;
        this.measurementMapper = measurementMapper;
        this.cloudinary = cloudinary;
    }

    @GetMapping
    public String getMeasurementLogPage(Model model) {
        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
        model.addAttribute("measurements", currentUser.getMeasurements().stream().map(measurementMapper::toDTO).collect(Collectors.toList()));
        return "measurement-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeasurement(@PathVariable Long id, Model model) throws IOException {
        if (Objects.nonNull(measurementService.findById(id).getPhoto())) {
            cloudinary.uploader().destroy(String.valueOf(id), Collections.emptyMap());
        }
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
        newMeasurement = measurementService.saveOrUpdate(new Measurement());
        model.addAttribute("measurement", measurementMapper.toDTO(newMeasurement));
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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String uploadPhoto(@RequestBody MultipartFile photo, RedirectAttributes redirectAttributes) throws IOException {

        int nameOfPhoto =newMeasurement.getId().intValue();

        cloudinary.uploader().upload(photo.getBytes(), ObjectUtils.asMap("public_id", String.valueOf(nameOfPhoto)));
        String generate = cloudinary.url().generate(String.valueOf(nameOfPhoto));
        return generate;
    }

//    @GetMapping("/{parameter}")
//    public String getMeasurementsTakenToday(@PathVariable String parameter, Model model) {
//        User currentUser = userService.findByUsername(Objects.requireNonNull(getPrincipal()).getUsername());
//        if (parameter.equals("today")) {
//            model.addAttribute("measurements", currentUser.getMeasurements().stream()
//                    .filter(measurement -> isToday(measurement.getFixationTime()))
//                    .sorted(Comparator.comparingLong(Measurement::getId))
//                    .map(measurementMapper::toDTO)
//                    .collect(Collectors.toList()));
//        } else if (parameter.equals("last7days")){
//            model.addAttribute("measurements", currentUser.getMeasurements().stream()
//                    .filter(measurement -> isLast7Days(measurement.getFixationTime()))
//                    .sorted(Comparator.comparingLong(Measurement::getId))
//                    .map(measurementMapper::toDTO)
//                    .collect(Collectors.toList()));
//        }
//        return "measurement-log";
//    }

    private UserDetails getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //вынести в отдельный метод и из него брать логин искать юзера и перед созданием нового изменения сетить этого юзера
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (UserDetails) auth.getPrincipal();
        }
        return null;
    }


//    private boolean isToday(LocalDateTime localDateTime) {
//        LocalDateTime now = LocalDateTime.now();
//        if (localDateTime.getYear() == now.getYear() && localDateTime.getDayOfYear() == now.getDayOfYear()) {
//            return true;
//        }
//        return false;
//    }
//
//    private boolean isLast7Days(LocalDateTime localDateTime) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime minus7Days = now.minusDays(7);
//        if (localDateTime.isAfter(minus7Days)) {
//            return true;
//        }
//        return false;
//    }


}
