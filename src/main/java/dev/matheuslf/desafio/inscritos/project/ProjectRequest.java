package dev.matheuslf.desafio.inscritos.project;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ProjectRequest(

        @NotNull(message = "Nome do Projeto é obrigatório")
        @Size(min = 3, max = 100)
        String name,
        String description,
        LocalDateTime endDate
) {
}
