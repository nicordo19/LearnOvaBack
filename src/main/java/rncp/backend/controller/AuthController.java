package rncp.backend.controller;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rncp.backend.dto.LoginRequest;
import rncp.backend.dto.LoginResponse;
import rncp.backend.entity.User;
import rncp.backend.sevice.AuthService ;

@RestController
@RequestMapping("api/auth")

public class AuthController {
//injection de dépendance
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }
// point d'entrée vers le back

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {

        ResponseEntity<LoginResponse> loginResponse = authService.login(loginRequest);

        if (loginResponse.getStatusCode().is2xxSuccessful()) {

            String token = loginResponse.getBody().getToken();

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60);

            response.addCookie(cookie);
        }

        return loginResponse;
    }

}
