package rncp.backend.dto;

import rncp.backend.entity.Comment;
import rncp.backend.entity.Role;
import rncp.backend.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class VideoCommentResponse {

    private UUID id;
    private String content;
    private LocalDateTime createdAt;
    private UUID authorId;
    private String authorFirstName;
    private String authorLastName;
    private String authorRole;

    public static VideoCommentResponse from(Comment comment) {
        VideoCommentResponse response = new VideoCommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());

        User author = comment.getUser();
        if (author != null) {
            response.setAuthorId(author.getId());
            response.setAuthorFirstName(author.getFirst_name());
            response.setAuthorLastName(author.getLast_name());
            response.setAuthorRole(toFrontendRole(author.getRole()));
        }

        return response;
    }

    private static String toFrontendRole(Role role) {
        if (role == null || role.getRoleName() == null) {
            return null;
        }

        if ("PROFESSEUR".equalsIgnoreCase(role.getRoleName())) {
            return "ROLE_PROF";
        }

        if ("ETUDIANT".equalsIgnoreCase(role.getRoleName())) {
            return "ROLE_USER";
        }

        return role.getRoleName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getAuthorRole() {
        return authorRole;
    }

    public void setAuthorRole(String authorRole) {
        this.authorRole = authorRole;
    }
}
