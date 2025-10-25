package dev.matheuslf.desafio.inscritos.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.matheuslf.desafio.inscritos.user.JWTUserData;
import dev.matheuslf.desafio.inscritos.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenConfig {

    @Value("${JWT_SECRET}")
    private String secret;


    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("userId", user.getId().toString())
                .withClaim("role", user.getRole().name())
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusMillis(36000000)) // 10 horas
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }


    public Optional<JWTUserData> validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);

            String email = decodedJWT.getSubject();
            String userId = decodedJWT.getClaim("userId").asString();
            String role = decodedJWT.getClaim("role").asString();

            if (email == null || userId == null) {
                return Optional.empty();
            }

            UUID userIdConvertido = UUID.fromString(userId);

            JWTUserData jwtUserData = new JWTUserData(userIdConvertido, email, role);

            return Optional.of(jwtUserData);

        }catch (JWTVerificationException e) {
            return Optional.empty();
        }

    }
}
