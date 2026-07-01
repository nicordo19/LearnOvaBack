package rncp.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import rncp.backend.dto.RegisterRequest;
import rncp.backend.dto.UserProfileResponse;
import rncp.backend.entity.Role;
import rncp.backend.entity.User;
import rncp.backend.repository.RoleRepository;
import rncp.backend.repository.UserRepository;
import rncp.backend.service.AuthService;
import rncp.backend.service.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceRegisterTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    RoleRepository roleRepository;
    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @Test
    void register_whenStudentRegisterIsValid_shouldReturnUserProfile() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Nicolas");
        request.setLastName("Poiraud");
        request.setEmail("poiraudnico@gmail.com");
        request.setPassword("password123");
        request.setProfession("ETUDIANT");
        request.setEtudiant(true);
        request.setProfesseur(false);

        // L'email n'existe pas encore en bdd
      when(userRepository.findByEmail(request.getEmail()))
              .thenReturn(Optional.empty());
     // Le role Etudiant existe deja
        Role role = new Role();
        role.setRoleName("ETUDIANT");

        when(roleRepository.findByRoleName("ETUDIANT"))
                .thenReturn(Optional.of(role));

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encoded-password");

        User savedUser = new User();
        savedUser.setFirst_name("Nicolas");
        savedUser.setLast_name("Poiraud");
        savedUser.setEmail("poiraudnico@gmail.com");
        savedUser.setPassword("encoded-password");
        savedUser.setRole(role);

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        UserProfileResponse response = authService.register(request);

        assertEquals("Nicolas", response.getFirstName());
        assertEquals("Poiraud", response.getLastName());
        assertEquals("poiraudnico@gmail.com", response.getEmail());
        assertEquals("ROLE_USER", response.getRole());

        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }
    @Test
    void register_whenEmailAlreadyExists_shouldThrowException() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Nicolas");
        request.setLastName("Poiraud");
        request.setEmail("poiraudnico@gmail.com");
        request.setPassword("password123");
        request.setProfession("ETUDIANT");
        request.setEtudiant(true);
        request.setProfesseur(false);

        User existingUser = new User();
        existingUser.setEmail(request.getEmail());
        // L'email existe en bdd
        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(existingUser));

        RuntimeException exception = assertThrows(
                RuntimeException.class, () -> authService.register(request)
        );

        assertEquals(
                "Un utilisateur avec cet email existe deja",
                exception.getMessage()
        );
        // verification que l'utilisateur n'est pas enrgistrés a nouveau
        verify(userRepository, never()).save(any(User.class));
    }
}
