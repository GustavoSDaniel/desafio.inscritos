package dev.matheuslf.desafio.inscritos.user;

import jakarta.validation.constraints.NotNull;

public record UserRequestUpdateRole(

        @NotNull(message = "Role é obrigatória")
        UserRole role
) {
}
