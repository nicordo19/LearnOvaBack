package rncp.backend;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import rncp.backend.service.CloudinaryService;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CloudinaryServiceTest {

    @Mock
    Cloudinary cloudinary;

    @Mock
    Uploader uploader;

    @Mock
    MultipartFile file;

    @InjectMocks
    CloudinaryService cloudinaryService;

    @Test
    void uploadVideo_whenCloudinaryReturnsSecureUrl_shouldReturnSecureUrl() throws IOException {
        // on teste le cas Happy Path.
        // Si Cloudinary renvoie une secure_url, notre service doit retourner cette URL au reste de l'application.
        byte[] videoBytes = "fake-video".getBytes();
        Map uploadResult = Map.of("secure_url", "https://res.cloudinary.com/demo/video/upload/video.mp4");

        when(file.getBytes()).thenReturn(videoBytes);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(eq(videoBytes), argThat(options -> "video".equals(options.get("resource_type")))))
                .thenReturn(uploadResult);

        String result = cloudinaryService.uploadVideo(file);

        assertEquals("https://res.cloudinary.com/demo/video/upload/video.mp4", result);
    }

    @Test
    void uploadVideoWithDetails_whenFileIsValid_shouldUploadAsVideo() throws IOException {
        // on teste que le service appelle Cloudinary avec le bon type de ressource.
        // L'option resource_type = video est importante car on envoie une video, pas une image.
        byte[] videoBytes = "fake-video".getBytes();
        Map uploadResult = Map.of(
                "secure_url", "https://res.cloudinary.com/demo/video/upload/video.mp4",
                "public_id", "video"
        );

        when(file.getBytes()).thenReturn(videoBytes);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(eq(videoBytes), argThat(options -> "video".equals(options.get("resource_type")))))
                .thenReturn(uploadResult);

        Map result = cloudinaryService.uploadVideoWithDetails(file);

        assertEquals(uploadResult, result);
        verify(uploader).upload(eq(videoBytes), argThat(options -> "video".equals(options.get("resource_type"))));
    }

    @Test
    void uploadVideoWithDetails_whenFileCannotBeRead_shouldThrowRuntimeException() throws IOException {
        // on teste le cas d'erreur simple.
        // Si Java n'arrive pas a lire le fichier, le service transforme l'IOException en RuntimeException.
        when(file.getBytes()).thenThrow(new IOException("lecture impossible"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> cloudinaryService.uploadVideoWithDetails(file)
        );

        assertEquals("erreur lors de l'upload video", exception.getMessage());
    }
}
