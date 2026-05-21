package rncp.backend.config;

import org.springframework.boot.CommandLineRunner;
import rncp.backend.entity.Role;
import rncp.backend.repository.RoleRepository;

public class RoleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.findByRoleName("ETUDIANT").isEmpty()) {
            roleRepository.save(new Role("ETUDIANT"));
        }
        if (roleRepository.findByRoleName("PROFESSEUR").isEmpty()) {
            roleRepository.save(new Role("PROFESSEUR"));
        }
    }
}

