package dev.matheuslf.desafio.inscritos.exception;

import dev.matheuslf.desafio.inscritos.project.*;
import dev.matheuslf.desafio.inscritos.task.*;
import dev.matheuslf.desafio.inscritos.user.AccessDeniedException;
import dev.matheuslf.desafio.inscritos.user.UserEmailDuplicateException;
import dev.matheuslf.desafio.inscritos.user.UserNameTooShortException;
import dev.matheuslf.desafio.inscritos.user.UserNotFoundException;
import dev.matheuslf.desafio.inscritos.util.DateEndBeforeStarDateException;
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

        log.warn("Nome do projeto muito curto {}", ex.getMessage());

        ErrorResponse ErrorNameShort = new ErrorResponse("Nome muito curto",
                "Project name must be at least 3 characters long. Provided",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorNameShort);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFoundException(
            ProjectNotFoundException ex){

        log.warn("Projeto não encontrado {}", ex.getMessage());

        ErrorResponse ErrorNotFound = new ErrorResponse("Projeto não encontrado",
                "O projeto com o ID especificado não foi encontrado.",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorNotFound);
    }

    @ExceptionHandler(ProjectNameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNameNotFoundException(
            ProjectNameNotFoundException ex){

        log.warn("Projeto com esse nome não encontrado {}", ex.getMessage());

        ErrorResponse ErrorNotFound = new ErrorResponse("Projeto não encontrado",
                "O projeto com o nome especificado não foi encontrado.",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorNotFound);
    }

    @ExceptionHandler(ProjectNameDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleProjectNameDuplicateException(
            ProjectNameDuplicateException ex){
        log.warn("Projeto com esse nome já existe {}", ex.getMessage());

        ErrorResponse ErrorDuplicate = new ErrorResponse("Nome já em Uso",
                "Já existe um projeto com esse nome.",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorDuplicate);
    }

    @ExceptionHandler(DateEndBeforeStarDateException.class)
    public ResponseEntity<ErrorResponse> handleDateEndBeforeStarDateException(
            DateEndBeforeStarDateException ex){
        log.warn("Data de término anterior à data de início {}", ex.getMessage());

        ErrorResponse ErrorDate = new ErrorResponse("Data Inválida",
                "A data de término não pode ser anterior à data de início.",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDate);
    }

    @ExceptionHandler(TaskNameInTheProjectDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleTaskNameInTheProjectDuplicateException
            (TaskNameInTheProjectDuplicateException ex){

        log.warn("Task com esse nome no projeto já existe {}", ex.getMessage());

        ErrorResponse ErrorDuplicate = new ErrorResponse("Nome já em uso nesse projeto",
                "Já existe uma task com esse titulo, nesse projeto",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorDuplicate);
    }

    @ExceptionHandler(TaskNameTooShortException.class)
    public ResponseEntity<ErrorResponse> handleTaskNameTooShortException(
            TaskNameTooShortException ex){

        log.warn("Nome da task muito curto {}", ex.getMessage());

        ErrorResponse ErrorNameShort = new ErrorResponse("Nome muito curto",
                "Task name must be at least 5 characters long. Provided",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorNameShort);
    }


    @ExceptionHandler(TaskNameDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleTaskNameDuplicateException(TaskNameDuplicateException ex){

        log.warn("Task com esse nome já existe {}", ex.getMessage());

        ErrorResponse ErrorDuplicate = new ErrorResponse("Nome já em uso",
                "Já existe uma task com esse titulo",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorDuplicate);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex){

        log.warn("Task não encontrado {}", ex.getMessage());

        ErrorResponse ErrorNotFound = new ErrorResponse("Task não encontrado",
                "A task com o ID informado não foi encontrado.",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorNotFound);
    }

    @ExceptionHandler(TaskStatusDoneException.class)
    public ResponseEntity<ErrorResponse> handleTaskStatusDoneException(TaskStatusDoneException ex){

        log.warn("Não é possivel atualizar uma tesk já finalizada");

        ErrorResponse ErrorTaskDone = new ErrorResponse("Task já finalizada",
                "Não é possível alterar uma task que já foi concluída",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorTaskDone);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
        log.warn("Usuario com esse ID não foi encontrado");

        ErrorResponse ErrorNotFound = new ErrorResponse("Usuario não encontrado",
                "Usuario com o ID informado não existe",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorNotFound);
    }

    @ExceptionHandler(UserEmailDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailDuplicateException
            (UserEmailDuplicateException ex){

        log.warn("Já existe um usuario com esse email {}", ex.getMessage());

        ErrorResponse ErrorDuplicate = new ErrorResponse("Email já em uso",
                "Já existe um usuario com esse email",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorDuplicate);
    }

    @ExceptionHandler(UserNameTooShortException.class)
    public ResponseEntity<ErrorResponse> handleUserNameTooShortException(
            UserNameTooShortException ex){

        log.warn("Nome do usuario muito curto {}", ex.getMessage());

        ErrorResponse ErrorNameShort = new ErrorResponse("Nome muito curto",
                "User name must be at least 4 characters long. Provided",
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorNameShort);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex){

        log.warn("Usuário não tem acesso para realizar essa função");

        ErrorResponse ErrorAccess = new ErrorResponse(
                "Usuário não tem permissão",
                "Você não tem permissão para acessar dados de outro usuário",
                LocalDateTime.now(),
                null
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorAccess);
    }


}
