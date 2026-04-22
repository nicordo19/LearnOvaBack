package rncp.backend.sevice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import rncp.backend.dto.LoginRequest;
import rncp.backend.dto.LoginResponse;
import rncp.backend.entity.User;
import rncp.backend.repository.RoleRepository;
import rncp.backend.repository.UserRepository;

import java.util.Optional;
@Service
public class AuthService {

// ingection de dependance

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;


  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
      this.jwtService = jwtService;
  }
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

      String token = jwtService.generateToken(loginRequest.getEmail());
      System.out.println(token);

        // 1. Vérifier si l'utilisateur existe
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body(new LoginResponse(token,"Invalid email or password"));
        }

        User user = userOptional.get();

        // 2. Vérifier le mot de passe
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(new LoginResponse(token,"Invalid email or password"));
        }

        // 3. Succès
        return ResponseEntity.ok(new LoginResponse(token, "accés Succés"));
    }
}
