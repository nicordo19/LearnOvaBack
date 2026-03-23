package rncp.backend.controller;
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
@RequestMapping("/auth")

public class AuthController {
//injection de dépendance
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }
// point d'entrée vers le back

    @PostMapping("/login")
    //ResponceEntity permet de controler les requette http
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        //renvois vers le service
        return authService.login(loginRequest);
    }


}
