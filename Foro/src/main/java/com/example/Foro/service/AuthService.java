package com.example.Foro.service;



import com.example.Foro.model.User;
import com.example.Foro.repository.UserRepository;
import com.example.Foro.dto.AuthRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final String SECRET_KEY = "secret";

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticate(AuthRequest authRequest) {
        // Aquí puedes implementar la lógica de autenticación con usuario y contraseña.
        // En este ejemplo, solo devolvemos un token si el usuario existe.
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = generateToken(user);
        return token;
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expira en 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Authentication getAuthenticationFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        UserDetails userDetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | JwtException e) {
            return false;
        }
    }

    public User register(User user) {
        // Lógica de registro de usuario
        return userRepository.save(user);
    }
}
