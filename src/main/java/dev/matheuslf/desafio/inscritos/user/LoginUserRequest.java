package dev.matheuslf.desafio.inscritos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(

        @NotBlank(message = "Para realizar o login o email é obrigatório")
        @Email(message = "O formato do email é inválido")
        String email,

        @NotBlank(message = "Para realizar o login a senha é obrigatório")
        String password
) {
}
