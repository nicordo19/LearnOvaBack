package rncp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rncp.backend.entity.User;
import rncp.backend.entity.Video;
import rncp.backend.entity.VideoLike;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VideoLikeRepository extends JpaRepository<VideoLike, UUID> {

    boolean existsByVideoAndUser(Video video, User user);

    long countByVideo(Video video);

    Optional<VideoLike> findByVideoAndUser(Video video, User user);

    List<VideoLike> findByUserOrderByCreatedAtDesc(User user);
}
