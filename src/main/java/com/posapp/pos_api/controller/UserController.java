package com.posapp.pos_api.controller;

import com.posapp.pos_api.model.Users;
import com.posapp.pos_api.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Api(tags = "User API", description = "Operations pertaining to users in the application")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint untuk registrasi
    @PostMapping("/register")
    @ApiOperation(value = "Register a new user", notes = "Provide name, email, and password to register")
    public ResponseEntity<Object> registerUser(@ApiParam(value = "User details to register", required = true) @RequestBody Users user) {
        
    Users registeredUser = userService.registerUser(user.getName(), user.getEmail(), user.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).body(
            Map.of(
                "message", "User registered successfully",
                "status", HttpStatus.CREATED.value(),
                "user", registeredUser
            )
        );
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login user", notes = "Provide email and password to authenticate the user and receive a JWT token")
    public ResponseEntity<Object> loginUser(@ApiParam(value = "User credentials for login", required = true) @RequestBody Users user) {
        String jwtToken = userService.loginUser(user.getEmail(), user.getPassword());

        return ResponseEntity.ok(
            Map.of(
                "message", "Login successful",
                "status", HttpStatus.OK.value(),
                "token", jwtToken
            )
        );
    }

}
