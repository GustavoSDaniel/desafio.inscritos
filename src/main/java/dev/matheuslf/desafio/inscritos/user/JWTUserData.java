package dev.matheuslf.desafio.inscritos.user;

import java.util.UUID;

public record JWTUserData(
        UUID userId,
        String email,
        String role
) {
}
