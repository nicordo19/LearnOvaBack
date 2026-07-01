package rncp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rncp.backend.entity.Comment;
import rncp.backend.entity.Video;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findByVideoOrderByCreatedAtAsc(Video video);
}
