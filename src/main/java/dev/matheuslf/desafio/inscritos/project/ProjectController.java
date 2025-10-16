package dev.matheuslf.desafio.inscritos.project;

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
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @Operation(summary = "Adicionando novo Projeto")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest projectRequest)
            throws ProjectNameTooShortException {

        ProjectResponse newProject = projectService.createProject(projectRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProject.id()).toUri();

        return ResponseEntity.created(location).body(newProject);
    }

    @GetMapping()
    @Operation(summary = "Mostra todos os Projetos")
    public ResponseEntity<Page<ProjectResponse>> mostrarTodosProjetos(
            @ParameterObject
            @PageableDefault(size = 20, sort = "startDate", direction = Sort.Direction.DESC)
            Pageable pageable)
    {
        Page<ProjectResponse> allProjects = projectService.getProjects(pageable );

        return ResponseEntity.ok(allProjects);

    }
}
