package by.zadziarnouski.workoutlog.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:cloudinary.properties")
public class CloudinaryConfiguration {

    @Value("${cloud.name}")
    private String cloud_name;
    @Value("${api.key}")
    private String api_key;
    @Value("${api.secret}")
    private String api_secret;

    @Bean
    public Cloudinary ConnectionToCloudinary() {

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloud_name,
                "api_key", api_key,
                "api_secret", api_secret));
        return cloudinary;
    }


}
