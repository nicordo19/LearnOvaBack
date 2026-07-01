package rncp.backend.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rncp.backend.dto.LoginRequest;
import rncp.backend.dto.LoginResponse;
import rncp.backend.dto.RegisterRequest;
import rncp.backend.dto.UserProfileResponse;
import rncp.backend.entity.Role;
import rncp.backend.entity.User;
import rncp.backend.repository.RoleRepository;
import rncp.backend.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserProfileResponse register(RegisterRequest request) {
        if ((request.isEtudiant() && request.isProfesseur()) || (!request.isEtudiant() && !request.isProfesseur())) {
            throw new RuntimeException("Vous devez choisir soit étudiant soit professeur");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe deja");
        }

        String wanted = request.isEtudiant() ? "ETUDIANT" : "PROFESSEUR";

        Role role = roleRepository.findByRoleName(wanted)
                .orElseGet(() -> roleRepository.save(new Role(wanted)));

        User user = new User();
        user.setRole(role);
        user.setFirst_name(request.getFirstName());
        user.setLast_name(request.getLastName());
        user.setEmail(request.getEmail());
        user.setProfession(request.getProfession());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return UserProfileResponse.from(savedUser);
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        // 1. Vérifier si l'utilisateur existe
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "Invalid email or password"));
        }

        User user = userOptional.get();

        // 2. Vérifier le mot de passe
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "Invalid email or password"));
        }

        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, "accés Succés"));
    }
}