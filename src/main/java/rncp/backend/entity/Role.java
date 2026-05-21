package rncp.backend.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {

    public Role() {
    }

    public Role(String roleName){
        this.roleName = roleName;
    }
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "rol_name", nullable = false)
    private String roleName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
