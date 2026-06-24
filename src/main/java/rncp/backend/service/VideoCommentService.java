package rncp.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rncp.backend.dto.CreateVideoCommentRequest;
import rncp.backend.dto.VideoCommentResponse;
import rncp.backend.entity.Comment;
import rncp.backend.entity.User;
import rncp.backend.entity.Video;
import rncp.backend.repository.CommentRepository;
import rncp.backend.repository.VideoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class VideoCommentService {

    private final CommentRepository commentRepository;
    private final VideoRepository videoRepository;

    public VideoCommentService(CommentRepository commentRepository, VideoRepository videoRepository) {
        this.commentRepository = commentRepository;
        this.videoRepository = videoRepository;
    }

    public List<VideoCommentResponse> getCommentsForVideo(UUID videoId) {
        Video video = findVideo(videoId);

        return commentRepository.findByVideoOrderByCreatedAtAsc(video)
                .stream()
                .map(VideoCommentResponse::from)
                .toList();
    }

    public VideoCommentResponse createCommentForVideo(
            UUID videoId,
            User author,
            CreateVideoCommentRequest request
    ) {
        if (request == null || request.getContent() == null || request.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le commentaire ne peut pas etre vide");
        }

        Video video = findVideo(videoId);
        Comment comment = new Comment(request.getContent().trim(), author, video);

        return VideoCommentResponse.from(commentRepository.save(comment));
    }

    private Video findVideo(UUID videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video introuvable"));
    }
}
