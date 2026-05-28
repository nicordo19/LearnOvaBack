package rncp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rncp.backend.entity.User;
import rncp.backend.entity.Video;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {

    List<Video> findByUserOrderByPublishedAtDesc(User user);

    Optional<Video> findByIdAndUser(UUID id, User user);
}
