package by.zadziarnouski.workoutlog.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationCloudinary {

    @Bean
    public Cloudinary ConnectionToCloudinary(){
        Cloudinary cloud = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "edmonddantes",
                "api_key", "591843375611729",
                "api_secret", "ZwF2t7X8A0b-yaxzVS8to0zcR3w"));
        return cloud;
    }
}
