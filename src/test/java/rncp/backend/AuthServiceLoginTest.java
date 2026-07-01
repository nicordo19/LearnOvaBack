package rncp.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import rncp.backend.dto.LoginRequest;
import rncp.backend.dto.LoginResponse;
import rncp.backend.entity.User;
import rncp.backend.repository.UserRepository;
import rncp.backend.service.AuthService;
import rncp.backend.service.JwtService;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
// teste Happy Path : test le cas ou tout ce passe bien

@ExtendWith(MockitoExtension.class)
public class AuthServiceLoginTest {
    @Mock
     UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @Test
    void LoginWhenUserAreCorect() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("1234");

        User user = new User();

        user.setEmail("test@gmail.com");
        user.setPassword("monMotDePasse");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(jwtService.generateToken(user.getEmail()))
                .thenReturn("fake-token");

        ResponseEntity<LoginResponse> response =
                authService.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("fake-token", response.getBody().getToken());
        assertEquals("accés Succés",response.getBody().getMessage());

    }
    @Test
    void login_shouldReturnUnautorized_whenEmailDoesNotExiste(){
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("1234");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        ResponseEntity<LoginResponse> response =
                authService.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertEquals(null,response.getBody().getToken());
        assertEquals("Invalid email or password",response.getBody().getMessage());

    }
    @Test
    void login_shouldReturnUnautorized_whenPasswordIsIncorrect() {

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("1234");

        User user = new User();

        user.setEmail("test@gmail.com");
        user.setPassword("monMotDePasse");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                .thenReturn(false);

        ResponseEntity<LoginResponse> response =
                authService.login(loginRequest);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(null, response.getBody().getToken());
        assertEquals("Invalid email or password",response.getBody().getMessage());

    }
}