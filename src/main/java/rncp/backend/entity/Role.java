package rncp.backend.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "rol_name", nullable = false)
    private String roleName;

}
