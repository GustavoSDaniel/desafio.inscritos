package dev.matheuslf.desafio.inscritos.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskRequestWhitProject(

        @NotBlank(message = "O titulo é obrigatório")
        @Size(min = 5, max = 150, message = "O título deve ter entre 5 e 150 caracteres")
        String title,
        @NotBlank(message = "A titulo é descrição é obrigatória")
        String description,
        @NotNull(message = "A prioridade é obrigatório")
        PriorityTask priority,
        @NotNull(message = "A data de termino da task é obrigatória")
        LocalDateTime dueDate,
        @NotNull(message = "O ID do projeto é obrigatório")
        Long projectId
        ) {
}
