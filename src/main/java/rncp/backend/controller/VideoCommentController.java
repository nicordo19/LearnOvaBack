package rncp.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rncp.backend.dto.CreateVideoCommentRequest;
import rncp.backend.dto.VideoCommentResponse;
import rncp.backend.entity.User;
import rncp.backend.service.VideoCommentService;

import java.util.List;
import java.util.UUID;

@RestController
public class VideoCommentController {

    private final VideoCommentService videoCommentService;

    public VideoCommentController(VideoCommentService videoCommentService) {
        this.videoCommentService = videoCommentService;
    }

    @GetMapping("/api/videos/{videoId}/comments")
    public List<VideoCommentResponse> getCommentsForVideo(@PathVariable UUID videoId) {
        return videoCommentService.getCommentsForVideo(videoId);
    }

    @PostMapping("/api/videos/{videoId}/comments")
    public VideoCommentResponse createCommentForVideo(
            @PathVariable UUID videoId,
            @RequestBody CreateVideoCommentRequest request,
            Authentication authentication
    ) {
        User author = (User) authentication.getPrincipal();
        return videoCommentService.createCommentForVideo(videoId, author, request);
    }
}
