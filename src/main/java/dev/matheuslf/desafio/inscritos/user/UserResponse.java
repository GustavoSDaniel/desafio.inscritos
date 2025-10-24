package dev.matheuslf.desafio.inscritos.user;

import java.util.UUID;

public record UserResponse(

        UUID id,
        String userName,
        String email,
        UserRole role

) {
}
