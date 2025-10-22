package dev.matheuslf.desafio.inscritos.project;

import dev.matheuslf.desafio.inscritos.user.*;
import dev.matheuslf.desafio.inscritos.util.DateEndBeforeStarDateException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final static Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest)
            throws ProjectNameTooShortException {

        log.info("Iniciando criação do projeto {} ", projectRequest.name());

        if (projectRequest.name().length() < 3) {
            throw new ProjectNameTooShortException();
        }

        if (projectRepository.existsByName(projectRequest.name())) {
            throw new ProjectNameDuplicateException();
        }

        if (projectRequest.endDate() != null && projectRequest.endDate().isBefore(LocalDateTime.now())) {
            throw new DateEndBeforeStarDateException();
        }

        User user = userRepository.findById(projectRequest.userId())
                .orElseThrow(UserNotFoundException::new);

        Project newProject = new Project();
        newProject.setName(projectRequest.name());
        newProject.setDescription(projectRequest.description());
        newProject.setEndDate(projectRequest.endDate());
        newProject.setUser(user);

        Project savedProject = projectRepository.save(newProject);

        log.info("Project {} salvo com sucesso", projectRequest.name());

        return projectMapper.toProjectResponse(savedProject);

    }

    @Override
    public Page<ProjectResponse> getProjects(Pageable pageable) {

        Page<Project> projectsAll = projectRepository.findAll(pageable);

        if (projectsAll.isEmpty()) {
            log.debug("Nenhum Projeto encontrado");

            return Page.empty(pageable);
        }

        log.debug("Retornando todos os projetos no total de {} ", projectsAll.getTotalElements());

        return projectsAll.map(projectMapper::toProjectResponse);
    }

    @Override
    public ProjectResponse getProjectById(Long id) {

        Project project = projectRepository
                .findById(id).orElseThrow(ProjectNotFoundException::new);

        log.debug("Retornando projeto com id {}, name {} ", id, project.getName());

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public List<ProjectResponse> getProjectsByName(String name) {

        List<Project> projects = projectRepository.searchByName(name);

        if(projects.isEmpty()){

            log.debug("Nenhum Projeto encontrado com esse nome: {} ", name);
            throw new ProjectNameNotFoundException();
        }

        return projects.stream().map(projectMapper::toProjectResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequestUpdate)
            throws ProjectNameTooShortException {

        Project existingProject = projectRepository
                .findById(id).orElseThrow(ProjectNotFoundException::new);

        if (projectRequestUpdate.name() != null) {

            if (projectRequestUpdate.name().length() < 3) {
                throw new ProjectNameTooShortException();
            }

            Optional<Project> nameDuplicate = projectRepository.findByName(projectRequestUpdate.name());

            if (nameDuplicate.isPresent() && !nameDuplicate.get().getId().equals(id)) {

                throw new ProjectNameDuplicateException();
            }

            existingProject.setName(projectRequestUpdate.name());
        }

        if (projectRequestUpdate.description() != null) {
            existingProject.setDescription(projectRequestUpdate.description());
        }

        if (projectRequestUpdate.endDate() != null) {

            if (projectRequestUpdate.endDate().isBefore(LocalDateTime.now())) {
                throw new DateEndBeforeStarDateException();
            }

            existingProject.setEndDate(projectRequestUpdate.endDate());
        }

        log.info("Projeto com id {} atualizado com sucesso", id);

        Project updatedProject = projectRepository.save(existingProject);

        return projectMapper.toProjectResponse(updatedProject);
    }

    @Override
    public void deleteProject(Long id) {

        Project project = projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);

        projectRepository.delete(project);

        log.info("Projeto com id={} deletado com sucesso", id);
    }
}
