package rncp.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//cogiguration cloudinary

@Configuration
public class CloudinaryConfig {
    //lire les valeurs dans application.propertise
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;
    // creation avec Spring de l'objet cloudinary
    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName.trim(),
                        "api_key", apiKey.trim(),
                        "api_secret", apiSecret.trim(),
                        "secure", true
                )
        );
    }

}
