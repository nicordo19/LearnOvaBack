package rncp.backend.dto;

import rncp.backend.entity.Role;
import rncp.backend.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfileResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String profession;
    private String role;
    private LocalDateTime createdAt;

    // renvois les information de l'utilisateur connecté
    public static UserProfileResponse from(User u) {
        UserProfileResponse r = new UserProfileResponse();
        r.setId(u.getId());
        r.setFirstName(u.getFirst_name());
        r.setLastName(u.getLast_name());
        r.setEmail(u.getEmail());
        r.setProfession(u.getProfession());

        String backendRole = (u.getRole() != null) ? u.getRole().getRoleName() : null;
        if (backendRole != null) {
            // Normalise pour frontend (option)
            if (backendRole.equalsIgnoreCase("PROFESSEUR")) r.setRole("ROLE_PROF");
            else if (backendRole.equalsIgnoreCase("ETUDIANT")) r.setRole("ROLE_USER");
            else r.setRole(backendRole);
        } else {
            r.setRole(null);
        }
        return r;
    }
    // verification de rol:  si null return le role-nom
    private static String extractRoleName(Role role) {
        return role == null ? null : role.getRoleName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
