package by.zadziarnouski.workoutlog.controller;

import com.cloudinary.api.exceptions.ApiException;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class OpenWeatherMapRestController {
    private final OWM owm;

    @Autowired
    public OpenWeatherMapRestController(OWM owm) {
        this.owm = owm;
    }

    @GetMapping(path = "/weather")
    public String getWeather(@RequestParam String city) {
        String response = "";
        CurrentWeather cwd;
        try {
            cwd = owm.currentWeatherByCityName(city);
        } catch (APIException e) {
            response += "The city was not found. Try to enter your city here <a href=\"https://openweathermap.org/find\">OpenWeatherMap</a> ";
            return response;
        }

        if (cwd.hasRespCode() && Objects.requireNonNull(cwd.getRespCode()) == 200) {

            if (cwd.hasCityName()) {
                response += "City: " + cwd.getCityName();
            }
            if (cwd.hasMainData() && cwd.getMainData().hasTempMax() && cwd.getMainData().hasTempMin()) {
                response += ", temperature: " + cwd.getMainData().getTempMax() + "..." + cwd.getMainData().getTempMin() + "°C";
            }
            if (cwd.hasWindData() && cwd.getWindData().hasSpeed()) {
                response += ", wind: " + cwd.getWindData().getSpeed() + " m/s";
            }
            if (cwd.hasCloudData() && cwd.getCloudData().hasCloud()) {
                response += ", cloud: " + cwd.getCloudData().getCloud() + " %.";
            }
        }
        return response;
    }

}
