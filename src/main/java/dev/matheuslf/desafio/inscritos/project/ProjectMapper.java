package dev.matheuslf.desafio.inscritos.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toProjectResponse(Project project) {
        if (project == null){
            return null;
        }

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getEndDate()
        );
    }
}
