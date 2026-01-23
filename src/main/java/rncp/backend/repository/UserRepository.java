package rncp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rncp.backend.entity.User;

import java.util.UUID;
// j'ai déclaré mon interface UserRipository et j'invoque spring Jpa pour lui dire
// que :  je travaille avec l'entity User qui a la clé primaire UUID

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // il crée automatiquemoent
    // save
    // findBiId
    // findAll
    // delate
}
