package dev.matheuslf.desafio.inscritos.task;

import jakarta.validation.constraints.NotNull;

public record RequestUpdate(

        @NotNull
        StatusTask statusTask
) {
}
