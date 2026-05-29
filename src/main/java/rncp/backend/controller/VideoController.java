package rncp.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rncp.backend.dto.VideoResponse;
import rncp.backend.dto.VideoUpdateRequest;
import rncp.backend.dto.VideoUploadResponse;
import rncp.backend.entity.User;
import rncp.backend.sevice.VideoService;

import java.util.List;
import java.util.UUID;

@RestController
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {

        this.videoService = videoService;

    }

    @PostMapping({"/api/videos/upload", "/video/upload"})
    public ResponseEntity<VideoUploadResponse> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        VideoUploadResponse response = videoService.uploadVideo(file, user, title, description);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/videos/my-videos")
    public List<VideoResponse> getMyVideos(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return videoService.getMyVideos(user);
    }

    @GetMapping("/api/videos/liked")
    public List<VideoResponse> getLikedVideos(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return videoService.getLikedVideos(user);
    }

    @GetMapping("/api/videos")
    public List<VideoResponse> getAllVideos(Authentication authentication) {
        return videoService.getAllVideos(getAuthenticatedUser(authentication));
    }

    @GetMapping("/api/videos/{id}")
    public VideoResponse getVideoById(@PathVariable UUID id, Authentication authentication) {
        return videoService.getVideoById(id, getAuthenticatedUser(authentication));
    }

    @PostMapping("/api/videos/{id}/like")
    public VideoResponse likeVideo(@PathVariable UUID id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return videoService.likeVideo(id, user);
    }

    @DeleteMapping("/api/videos/{id}/like")
    public VideoResponse unlikeVideo(@PathVariable UUID id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return videoService.unlikeVideo(id, user);
    }

    @PutMapping("/api/videos/{id}")
    public VideoResponse updateVideo(
            @PathVariable UUID id,
            @RequestBody VideoUpdateRequest request,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return videoService.updateVideo(id, user, request);
    }

    @DeleteMapping("/api/videos/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable UUID id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        videoService.deleteVideo(id, user);
        return ResponseEntity.noContent().build();
    }

    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }
}
