package dev.matheuslf.desafio.inscritos.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest project) throws ProjectNameTooShortException;

    Page<ProjectResponse> getProjects(Pageable pageable);
}
