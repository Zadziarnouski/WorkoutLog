package by.zadziarnouski.workoutlog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
public class MeasurementDTO {
    private Long id;
    private float weight;
    private float height;
    private float neck;
    private float arms;
    private float forearms;
    private float chest;
    private float waist;
    private float buttocks;
    private float thighs;
    private float calves;
    private Long userID;

}
