package dev.matheuslf.desafio.inscritos.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.matheuslf.desafio.inscritos.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenConfig {

    @Value("${JWT_SECRET}")
    private String secret;


    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", user.getId().toString())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusMillis(36000000)) // 10 horas
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }
}
