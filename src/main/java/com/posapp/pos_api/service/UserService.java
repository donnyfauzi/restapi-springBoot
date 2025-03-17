package com.posapp.pos_api.service;

import com.posapp.pos_api.model.Users;
import com.posapp.pos_api.repository.UserRepository;
import com.posapp.pos_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService implements UserDetailsService {  // 🔹 Tambahkan ini

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public Users registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists!");
        }

        String hashedPassword = passwordEncoder.encode(password);
        Users newUser = new Users();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        userRepository.save(newUser);

        return newUser;
    }

    public String loginUser(String email, String password) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

   
}
