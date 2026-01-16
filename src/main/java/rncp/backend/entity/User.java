package rncp.backend.entity;

import jakarta.persistence.*;
import rncp.backend.entity.Role;

import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at")
    private  LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
