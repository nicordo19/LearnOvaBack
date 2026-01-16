package rncp.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "video_id")
    private Video video;

    public Comment() {
    }

    public Comment(String content, User user, Video video) {
        this.content = content;
        this.user = user;
        this.video = video;
        this.createdAt = LocalDateTime.now();
    }
}