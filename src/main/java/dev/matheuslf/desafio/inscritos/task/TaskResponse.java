package dev.matheuslf.desafio.inscritos.task;


import java.time.LocalDateTime;

public record TaskResponse(

        Long id,
        String title,
        String description,
        StatusTask status,
        PriorityTask priority,
        LocalDateTime dueDate,
        String userName,
        String projectName
) {
}
