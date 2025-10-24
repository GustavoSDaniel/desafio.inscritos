package dev.matheuslf.desafio.inscritos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserRequestUpdate(

        @Size(min = 4, message = "O nome tem que ter no minimo quatro letras")
        String userName,

        @Email(message = "O formato do email é inválido")
        String email,

        @Size(min = 6, message = "A senha deve conter no minimo 6 caracteres")
        String password
) {
}
