package rncp.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import rncp.backend.dto.VideoUploadResponse;
import rncp.backend.entity.User;
import rncp.backend.entity.Video;
import rncp.backend.repository.VideoLikeRepository;
import rncp.backend.repository.VideoRepository;
import rncp.backend.service.CloudinaryService;
import rncp.backend.service.VideoService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @Mock
    CloudinaryService cloudinaryService;

    @Mock
    VideoRepository videoRepository;

    @Mock
    VideoLikeRepository videoLikeRepository;

    @Mock
    MultipartFile file;

    @InjectMocks
    VideoService videoService;

    @Test
    void uploadVideo_whenFileIsValid_shouldUploadToCloudinaryAndSaveVideo() {
        //  ce test verifie le parcours principal de l'upload video. Happy Path
        // Le service doit envoyer le fichier a Cloudinary, creer une Video, puis sauvegarder son URL en base.
        User user = new User();
        Map uploadResult = Map.of(
                "secure_url", "https://res.cloudinary.com/demo/video/upload/video.mp4",
                "public_id", "video-public-id"
        );

        when(file.isEmpty()).thenReturn(false);
        when(cloudinaryService.uploadVideoWithDetails(file)).thenReturn(uploadResult);

        VideoUploadResponse response = videoService.uploadVideo(file, user, "Cours Java", "Introduction aux tests");

        assertEquals("https://res.cloudinary.com/demo/video/upload/video.mp4", response.getUrl());
        assertEquals("https://res.cloudinary.com/demo/video/upload/video.mp4", response.getSecureUrl());
        assertEquals("video-public-id", response.getPublicId());

        ArgumentCaptor<Video> videoCaptor = ArgumentCaptor.forClass(Video.class);
        verify(videoRepository).save(videoCaptor.capture());

        Video savedVideo = videoCaptor.getValue();
        assertEquals("https://res.cloudinary.com/demo/video/upload/video.mp4", savedVideo.getUrl());
        assertEquals("Cours Java", savedVideo.getTitle());
        assertEquals("Introduction aux tests", savedVideo.getDescription());
        assertSame(user, savedVideo.getUser());
    }

    @Test
    void uploadVideo_whenFileIsEmpty_shouldThrowBadRequestAndNotSaveVideo() {
        // ce test verifie un cas d'erreur important.
        // Si aucun fichier n'est fourni, on refuse la requete et on ne fait ni upload Cloudinary ni sauvegarde en base.
        when(file.isEmpty()).thenReturn(true);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> videoService.uploadVideo(file, new User(), "Cours Java", "Description")
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Le fichier video est obligatoire", exception.getReason());
        verify(cloudinaryService, never()).uploadVideoWithDetails(any());
        verify(videoRepository, never()).save(any());
    }

    @Test
    void uploadVideo_whenTitleAndDescriptionAreBlank_shouldSaveNullValues() {
        // ce test verifie une petite regle metier du service.
        // Les champs vides ne sont pas sauvegardes comme du texte vide, ils deviennent null.
        User user = new User();
        Map uploadResult = Map.of("secure_url", "https://res.cloudinary.com/demo/video/upload/video.mp4");

        when(file.isEmpty()).thenReturn(false);
        when(cloudinaryService.uploadVideoWithDetails(file)).thenReturn(uploadResult);

        videoService.uploadVideo(file, user, "   ", "");

        ArgumentCaptor<Video> videoCaptor = ArgumentCaptor.forClass(Video.class);
        verify(videoRepository).save(videoCaptor.capture());

        Video savedVideo = videoCaptor.getValue();
        assertNull(savedVideo.getTitle());
        assertNull(savedVideo.getDescription());
    }
}
