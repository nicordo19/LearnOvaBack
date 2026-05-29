package rncp.backend.dto;

import rncp.backend.entity.Video;

import java.time.LocalDateTime;
import java.util.UUID;

public class VideoResponse {

    private UUID id;
    private String title;
    private String url;
    private LocalDateTime createdAt;
    private String description;
    private UUID ownerId;
    private String userFirstName;
    private String userLastName;
    private boolean likedByCurrentUser;
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
