package dev.matheuslf.desafio.inscritos.project;

import java.time.LocalDateTime;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        LocalDateTime endDate,
        String userName
) {
}
