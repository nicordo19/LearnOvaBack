package rncp.backend.sevice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import rncp.backend.dto.VideoResponse;
import rncp.backend.dto.VideoUpdateRequest;
import rncp.backend.dto.VideoUploadResponse;
import rncp.backend.entity.User;
import rncp.backend.entity.Video;
import rncp.backend.repository.VideoRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VideoService {

    private final CloudinaryService cloudinaryService;
    private final VideoRepository videoRepository;

    public VideoService(CloudinaryService cloudinaryService, VideoRepository videoRepository) {
        this.cloudinaryService = cloudinaryService;
        this.videoRepository = videoRepository;
    }

    public VideoUploadResponse uploadVideo(MultipartFile file, User user, String title, String description) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le fichier video est obligatoire");
        }

        Map uploadResult = cloudinaryService.uploadVideoWithDetails(file);
        String secureUrl = getStringValue(uploadResult, "secure_url");
        String url = secureUrl != null ? secureUrl : getStringValue(uploadResult, "url");
        String publicId = getStringValue(uploadResult, "public_id");

        Video video = new Video();
        video.setUrl(url);
        video.setUser(user);
        video.setTitle(emptyToNull(title));
        video.setDescription(emptyToNull(description));
        videoRepository.save(video);

        return new VideoUploadResponse(url, secureUrl, publicId);
    }

    public List<VideoResponse> getMyVideos(User user) {
        return videoRepository.findByUserOrderByPublishedAtDesc(user)
                .stream()
                .map(VideoResponse::from)
                .toList();
    }

    public List<VideoResponse> getAllVideos() {
        return videoRepository.findAllByOrderByPublishedAtDesc()
                .stream()
                .map(VideoResponse::from)
                .toList();
    }

    public VideoResponse updateVideo(UUID id, User user, VideoUpdateRequest request) {
        Video video = videoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video introuvable"));

        video.setTitle(emptyToNull(request.getTitle()));
        video.setDescription(emptyToNull(request.getDescription()));

        return VideoResponse.from(videoRepository.save(video));
    }

    public void deleteVideo(UUID id, User user) {
        Video video = videoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video introuvable"));

        videoRepository.delete(video);
    }

    private String getStringValue(Map values, String key) {
        Object value = values.get(key);
        return value != null ? value.toString() : null;
    }

    private String emptyToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value;
    }
}
