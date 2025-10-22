package dev.matheuslf.desafio.inscritos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

        @NotBlank(message = "Para criar registro é obrigatório nome")
        @Size(min = 4)
        String userName,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O formato do email é inválido")
        String email,

        @NotBlank(message = "Para criar registro é obrigatório senha")
        String password
) {
}
