package dev.matheuslf.desafio.inscritos.project;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ProjectRequestUpdate(

        @Size(min = 3, max = 100, message = "Se fornecido, o nome do Projeto deve ter entre 3 e 100 caracteres")
        String name,
        String description,
        LocalDateTime endDate
) {
}
