package com.posapp.pos_api.controller;

import com.posapp.pos_api.model.Users;
import com.posapp.pos_api.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// Import Swagger
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registrasi pengguna baru", description = "Mendaftarkan pengguna ke dalam sistem.")
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Users user) {
        Users registeredUser = userService.registerUser(user.getName(), user.getEmail(), user.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "message", "User registered successfully",
                        "status", HttpStatus.CREATED.value(),
                        "user", registeredUser));
    }

    @Operation(summary = "Login ke sistem", description = "Menghasilkan token JWT untuk autentikasi.")
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Users user) {
        String jwtToken = userService.loginUser(user.getEmail(), user.getPassword());

        return ResponseEntity.ok(
        Map.of(
        "message", "Login successful",
        "status", HttpStatus.OK.value(),
        "token", jwtToken));
    }
    

}
