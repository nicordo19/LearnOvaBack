package rncp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rncp.backend.entity.Video;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
}
