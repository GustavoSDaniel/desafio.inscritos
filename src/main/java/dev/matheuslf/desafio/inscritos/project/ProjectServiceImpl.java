package dev.matheuslf.desafio.inscritos.project;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final static Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest)
            throws ProjectNameTooShortException {

        log.info("Iniciando criação do projeto {} ", projectRequest.name());

        if (projectRequest.name().length() < 3) {
            throw new ProjectNameTooShortException();
        }

        Project newProject = new Project();
        newProject.setName(projectRequest.name());
        newProject.setDescription(projectRequest.description());

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

        log.debug("Retornando todos os projetos {} no total de {} ", projectsAll.getNumberOfElements(),
                projectsAll.getTotalElements());

        return projectsAll.map(projectMapper::toProjectResponse);
    }
}
