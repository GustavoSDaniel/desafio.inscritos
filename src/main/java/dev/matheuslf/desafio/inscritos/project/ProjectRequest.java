package dev.matheuslf.desafio.inscritos.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectRequest(

        @NotBlank(message = "Nome do Projeto é obrigatório")
        @Size(min = 3, max = 100)
        String name,
        String description,
        @NotNull(message = "A data de termino do projeto é obrigatório")
        LocalDateTime endDate,
        @NotNull(message = "O ID do Usuario é obrigatório")
        UUID userId
) {
}
