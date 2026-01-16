package rncp.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControleur {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
