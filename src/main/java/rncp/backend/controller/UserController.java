package rncp.backend.controller;

import rncp.backend.dto.RegisterRequest ;
import org.springframework.web.bind.annotation.*;
import rncp.backend.entity.User;
import rncp.backend.sevice.UserService;

@RestController

@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User createUser(@RequestBody RegisterRequest request) {
        return userService.createUser(request);
    }
}