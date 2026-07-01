package rncp.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import rncp.backend.entity.Video;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Informations retournees pour une video.")
public class VideoResponse {

    @Schema(description = "Identifiant unique de la video.", example = "5a77522a-4fa1-4b97-b355-961c6df34221")
    private UUID id;

    @Schema(description = "Titre de la video.", example = "Introduction a Spring Boot")
    private String title;

    @Schema(description = "URL de lecture ou de telechargement de la video.", example = "https://res.cloudinary.com/demo/video/upload/video.mp4")
    private String url;

    @Schema(description = "Date de publication de la video.", example = "2026-06-30T14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Description de la video.", example = "Cours de presentation des bases de Spring Boot.")
    private String description;

    @Schema(description = "Identifiant du proprietaire de la video.", example = "1c9d4a8e-8a1b-4f4f-9f64-54c8f8f7a123")
    private UUID ownerId;

    @Schema(description = "Prenom du proprietaire de la video.", example = "Nicolas")
    private String userFirstName;

    @Schema(description = "Nom du proprietaire de la video.", example = "Poiraud")
    private String userLastName;

    @Schema(description = "Indique si l'utilisateur connecte a deja like cette video.", example = "false")
    private boolean likedByCurrentUser;

    @Schema(description = "Nombre total de likes de la video.", example = "12")
    private long likesCount;

    public static VideoResponse from(Video video) {
        return from(video, false, 0);
    }

    public static VideoResponse from(Video video, boolean likedByCurrentUser, long likesCount) {
        VideoResponse response = new VideoResponse();
        response.setId(video.getId());
        response.setTitle(video.getTitle());
        response.setUrl(video.getUrl());
        response.setCreatedAt(video.getPublishedAt());
        response.setDescription(video.getDescription());
        response.setLikedByCurrentUser(likedByCurrentUser);
        response.setLikesCount(likesCount);

        if (video.getUser() != null) {
            response.setOwnerId(video.getUser().getId());
            response.setUserFirstName(video.getUser().getFirst_name());
            response.setUserLastName(video.getUser().getLast_name());
        }

        return response;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public boolean isLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }
}
