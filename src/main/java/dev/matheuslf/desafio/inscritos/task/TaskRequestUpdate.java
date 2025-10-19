package dev.matheuslf.desafio.inscritos.task;

import jakarta.validation.constraints.NotNull;

public record TaskRequestUpdate(

        @NotNull
        StatusTask statusTask
) {
}
