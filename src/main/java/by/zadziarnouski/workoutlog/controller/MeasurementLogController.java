package by.zadziarnouski.workoutlog.controller;


import by.zadziarnouski.workoutlog.dto.MeasurementDTO;
import by.zadziarnouski.workoutlog.mapper.MeasurementMapper;
import by.zadziarnouski.workoutlog.model.Measurement;
import by.zadziarnouski.workoutlog.model.User;
import by.zadziarnouski.workoutlog.service.MeasurementService;
import by.zadziarnouski.workoutlog.service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/measurement-log")
public class MeasurementLogController {
    private final MeasurementService measurementService;
    private final MeasurementMapper measurementMapper;
    private final Cloudinary cloudinary;
    private static final Logger logger = LoggerFactory.getLogger(MeasurementLogController.class);

    @Autowired
    public MeasurementLogController(MeasurementService measurementService, MeasurementMapper measurementMapper, Cloudinary cloudinary) {
        this.measurementService = measurementService;
        this.measurementMapper = measurementMapper;
        this.cloudinary = cloudinary;
    }

    @GetMapping

    public String getMeasurementLogPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("measurements", measurementService.findAll().stream()     //Вынести метод в репозиторий( List<User> findAllOfCurrentUser(User currentUser))
                .map(measurementMapper::toDTO)
                .filter(measurementDTO -> measurementDTO.getUserID().equals(user.getId()))
                .collect(Collectors.toList()));
        return "measurement-log";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeasurement(@PathVariable Long id) throws IOException {
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
    public String getCreatePageForMeasurement(HttpSession session, Model model) {
        Measurement newMeasurement = measurementService.saveOrUpdate(new Measurement());
        session.setAttribute("measurement", newMeasurement);
        model.addAttribute("measurement", measurementMapper.toDTO(newMeasurement));
        return "create-update-measurement";
    }

    @PostMapping("/create-update")
    public String createUpdateMeasurement(@ModelAttribute MeasurementDTO measurementDTO, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        measurementDTO.setUserID(user.getId());
        Measurement measurement = measurementService.saveOrUpdate(measurementMapper.toEntity(measurementDTO));
        session.setAttribute("measurement", measurement);
        model.addAttribute("measurement", measurementMapper.toDTO(measurement));
        return "result-create-or-update-measurement";
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String uploadPhoto(@RequestBody MultipartFile photo, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
        Measurement measurement = (Measurement) session.getAttribute("measurement");
        int nameOfPhoto = measurement.getId().intValue();
        cloudinary.uploader().upload(photo.getBytes(), ObjectUtils.asMap("public_id", String.valueOf(nameOfPhoto)));
        logger.info("Photo with NAME=" + nameOfPhoto + " has been uploaded to the Cloudinary server");
        return cloudinary.url().generate(String.valueOf(nameOfPhoto));
    }

}
