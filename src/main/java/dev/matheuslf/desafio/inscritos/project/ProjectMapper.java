package dev.matheuslf.desafio.inscritos.project;

import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toProject(ProjectRequest projectRequest) {

        if (projectRequest == null) return null;

        return new Project(
                projectRequest.name(),
                projectRequest.description(),
                projectRequest.endDate()
        );

    }

    public ProjectResponse toProjectResponse(Project project) {
        if (project == null){
            return null;
        }

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getEndDate(),
                project.getUser() != null ? project.getUser().getUserName() : null
        );
    }
}
