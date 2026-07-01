package rncp.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
//creation de service Cloudinary
public class CloudinaryService {

    //injection de dépendance cloudinary : on recuper l'ojet dans cloudinaryConfig
    private final Cloudinary cloudinary ;

    // instotiation  dans le constructeur de Cloudinary
    public CloudinaryService(Cloudinary cloudinary) {

        this.cloudinary = cloudinary;

    }
    // creation de la fonction d'uplaod avec en paramettre MultiPartFile : ce qui fait qu'on stock
    // la video temporairement  en memoir et c'est manipuler en Java

    public String uploadVideo(MultipartFile file){
        Map uploadResult = uploadVideoWithDetails(file);
        return uploadResult.get("secure_url").toString();
    }

    public Map uploadVideoWithDetails(MultipartFile file){

        try{

            return cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "video"
                    )
            );
        }catch (IOException e){
            throw new RuntimeException("erreur lors de l'upload video");
        }
    }
}
