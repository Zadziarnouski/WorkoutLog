package by.zadziarnouski.workoutlog.configuration;

import net.aksingh.owmjapis.core.OWM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:owm.properties")
public class OpenWeatherMapConfiguration {
    @Value("${api.key.owm}")
    private String api_key;

    @Bean
    public OWM openWeatherMap() {
        OWM owm = new OWM(api_key);
        owm.setUnit(OWM.Unit.METRIC);
        return owm;
    }

}
