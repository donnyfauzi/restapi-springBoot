package com.posapp.pos_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.posapp.pos_api.config.JwtConfig;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY;
    private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private final long EXPIRATION_TIME; // Perbaiki tipe data

    @Autowired
    public JwtUtil(JwtConfig jwtConfig) {
        // Mendapatkan nilai dari application.properties
        this.SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.EXPIRATION_TIME = jwtConfig.getExpirationTime(); // Pastikan tipe data sudah Long

        System.out.println("Secret Key Length: " + jwtConfig.getSecretKey().length());

    }

    

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        return jws.getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email); // Gunakan email sebagai subject
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Gunakan variabel yang sudah benar
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }

    public Boolean validateToken(String token, String email) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(email) && !isTokenExpired(token));
    }
}
