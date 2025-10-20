package dev.matheuslf.desafio.inscritos.task;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Criar nova Task")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest taskRequest)
            throws TaskNameTooShortException {

        TaskResponse newTask = taskService.createTask(taskRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTask.id()).toUri();

        return ResponseEntity.created(location).body(newTask);
    }

    @GetMapping()
    @Operation(summary = "Mostrar todas as tasks")
    public ResponseEntity<Page<TaskResponse>> findAllTasks(
            @ParameterObject
            @PageableDefault(size = 20, sort = "startDate", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<TaskResponse> allTasks = taskService.getAllTask(pageable);

        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisar task por ID")
    public ResponseEntity<TaskResponse> findTaskById(@PathVariable Long id) {

        TaskResponse task = taskService.getTaskById(id);

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar status da Task por Id")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TaskRequestUpdate taskRequestUpdate) {

        TaskResponse updatedTask = taskService.updateTask(id, taskRequestUpdate);

        return ResponseEntity.ok(updatedTask);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Task por Id")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }


}
