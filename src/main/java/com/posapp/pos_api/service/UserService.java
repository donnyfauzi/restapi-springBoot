package com.posapp.pos_api.service;

import com.posapp.pos_api.model.Users;
import com.posapp.pos_api.repository.UserRepository;
import com.posapp.pos_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // Metode registrasi
    public Users registerUser(String name, String email, String password) {
        // Cek apakah email sudah ada
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists!");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(password);

        // Simpan pengguna baru
        Users newUser = new Users();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);

        return newUser;
    }

    // Metode login
    public String loginUser(String email, String password) {
        // Temukan user berdasarkan email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        // Verifikasi password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password!");
        }

        // Generate JWT Token
        return jwtUtil.generateToken(user.getEmail());
    }
}
