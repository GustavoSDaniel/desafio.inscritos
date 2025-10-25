package dev.matheuslf.desafio.inscritos.user;

import java.util.UUID;

public record UserRegisterResponse(

        UUID id,
        String email,
        String userName,
        UserRole role

) {
}
