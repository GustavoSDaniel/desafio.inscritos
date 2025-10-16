package dev.matheuslf.desafio.inscritos.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    ProjectResponse createProject(ProjectRequest project) throws ProjectNameTooShortException;

    Page<ProjectResponse> getProjects(Pageable pageable);

    ProjectResponse getProjectById(Long id);

    List<ProjectResponse> getProjectsByName(String name);

    ProjectResponse updateProject(Long id, ProjectRequest project) throws ProjectNameTooShortException;

    void deleteProject(Long id);
}
