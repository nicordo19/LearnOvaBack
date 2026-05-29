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
import rncp.backend.entity.VideoLike;
import rncp.backend.repository.VideoLikeRepository;
import rncp.backend.repository.VideoRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService {

    private final CloudinaryService cloudinaryService;
    private final VideoRepository videoRepository;
    private final VideoLikeRepository videoLikeRepository;

    public VideoService(
            CloudinaryService cloudinaryService,
            VideoRepository videoRepository,
            VideoLikeRepository videoLikeRepository
    ) {
        this.cloudinaryService = cloudinaryService;
        this.videoRepository = videoRepository;
        this.videoLikeRepository = videoLikeRepository;
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
                .map(video -> toResponse(video, user))
                .toList();
    }

    public List<VideoResponse> getAllVideos(User currentUser) {
        return videoRepository.findAllByOrderByPublishedAtDesc()
                .stream()
                .map(video -> toResponse(video, currentUser))
                .toList();
    }

    public VideoResponse getVideoById(UUID id, User currentUser) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video introuvable"));

        return toResponse(video, currentUser);
    }

    public VideoResponse updateVideo(UUID id, User user, VideoUpdateRequest request) {
        Video video = videoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video introuvable"));

        video.setTitle(emptyToNull(request.getTitle()));
        video.setDescription(emptyToNull(request.getDescription()));

        return toResponse(videoRepository.save(video), user);
    }

    public void deleteVideo(UUID id, User user) {
        Video video = videoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video introuvable"));

        videoRepository.delete(video);
    }

    public VideoResponse likeVideo(UUID id, User user) {
        Video video = findVideo(id);
        ensureUserDoesNotOwnVideo(video, user);

        if (!videoLikeRepository.existsByVideoAndUser(video, user)) {
            VideoLike videoLike = new VideoLike();
            videoLike.setVideo(video);
            videoLike.setUser(user);
            videoLikeRepository.save(videoLike);
        }

        return toResponse(video, user);
    }

    public VideoResponse unlikeVideo(UUID id, User user) {
        Video video = findVideo(id);

        videoLikeRepository.findByVideoAndUser(video, user)
                .ifPresent(videoLikeRepository::delete);

        return toResponse(video, user);
    }

    public List<VideoResponse> getLikedVideos(User user) {
        return videoLikeRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(VideoLike::getVideo)
                .map(video -> toResponse(video, user))
                .toList();
    }

    private Video findVideo(UUID id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video introuvable"));
    }

    private void ensureUserDoesNotOwnVideo(Video video, User user) {
        if (video.getUser() != null && Objects.equals(video.getUser().getId(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de liker sa propre video");
        }
    }

    private VideoResponse toResponse(Video video, User currentUser) {
        boolean likedByCurrentUser = currentUser != null && videoLikeRepository.existsByVideoAndUser(video, currentUser);
        long likesCount = videoLikeRepository.countByVideo(video);

        return VideoResponse.from(video, likedByCurrentUser, likesCount);
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
