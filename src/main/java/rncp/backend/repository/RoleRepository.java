package rncp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rncp.backend.entity.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByRoleName(String roleName);
}
