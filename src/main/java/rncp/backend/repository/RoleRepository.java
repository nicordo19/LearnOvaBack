package rncp.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rncp.backend.entity.Role;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(String roleName);
}
