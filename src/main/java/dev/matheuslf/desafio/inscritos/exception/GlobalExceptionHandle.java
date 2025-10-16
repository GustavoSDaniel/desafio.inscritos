package dev.matheuslf.desafio.inscritos.exception;

import dev.matheuslf.desafio.inscritos.project.ProjectNameTooShortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandle {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex){

        log.warn("Validação falhou {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        ErrorResponse erros = new ErrorResponse("Validação Falhou",
                "Erro de validação nos campos",
                LocalDateTime.now(),
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }



    @ExceptionHandler(ProjectNameTooShortException.class)
    public ResponseEntity<ErrorResponse> handleProjectNameTooShortException(
            ProjectNameTooShortException ex){
        ErrorResponse ErrorNameShort = new ErrorResponse("Nome muito curto",
                "Project name must be at least 3 characters long. Provided",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorNameShort);
    }


}
