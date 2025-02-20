package com.posapp.pos_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    private final String secretKey;
    private final Long expirationTime;

    public JwtConfig(
        @Value("${jwt.secret}") String secretKey, 
        @Value("${jwt.expiration}") Long expirationTime) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Long getExpirationTime() { // Tidak static
        return expirationTime;
    }
}
