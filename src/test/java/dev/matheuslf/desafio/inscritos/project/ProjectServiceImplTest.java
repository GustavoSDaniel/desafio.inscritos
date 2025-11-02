package dev.matheuslf.desafio.inscritos.project;

import dev.matheuslf.desafio.inscritos.user.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    //Arrange (preparação), act (ação), assert (Verificação)

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Nested
    class createProject {

        @Test
        @DisplayName("Should create project with success")
        void shouldCreateProjectWithSuccess() throws ProjectNameTooShortException {

            var userId = UUID.randomUUID();
            var endDate = LocalDateTime.parse("2026-12-31T12:30:59");

            var userFound = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );


            ProjectRequest input = new ProjectRequest(
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    userId

            );

            Project projectSalved = new Project(
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    userFound

            );


            ProjectResponse projectResponse = new ProjectResponse(
                    1L,
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    "gustavo"
            );

            when(userRepository.findById((userId))).thenReturn(Optional.of(userFound));

            when(projectRepository.existsByName(input.name())).thenReturn(false);

            when(projectRepository.save(any(Project.class))).thenReturn(projectSalved);

            when(projectMapper.toProjectResponse(projectSalved)).thenReturn(projectResponse);


            var output = projectService.createProject(input);

            assertNotNull(output);

            verify(projectRepository).save(any(Project.class));
            verify(projectMapper).toProjectResponse(projectSalved);
            verify(userRepository).findById(userId);
            verify(projectRepository).existsByName(input.name());

        }
    }

    @Nested
    class findProjectById {

        @Test
        @DisplayName("Should find project by id")
        void shouldFindProjectById() {

            var projectId = 1L;
            var endDate = LocalDateTime.parse("2026-12-31T12:30:59");

            var userFound = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            Project projectFound = new Project(
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    userFound

            );

            ProjectResponse projectResponse = new ProjectResponse(
                    1L,
                    "Projeto para teste",
                    "Projeto para testar projeto",
                     endDate,
                    "gustavo"
            );

            when(projectRepository.findById((projectId))).thenReturn(Optional.of(projectFound));
            when(projectMapper.toProjectResponse(projectFound)).thenReturn(projectResponse);

            var output = projectService.getProjectById(projectId);

            assertNotNull(output);

            verify(projectRepository).findById(projectId);
            verify(projectMapper).toProjectResponse(projectFound);

        }
    }

    @Nested
    class findProjectByName {

        @Test
        @DisplayName("Should find project by name")
        void shouldFindProjectByName() {

            var nameToSearch = "Project";
            var endDate = LocalDateTime.parse("2026-12-31T12:30:59");

            var projectId = 1L;
            var projectId2 = 2L;
            var projectId3 = 3L;


            var userManager = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            Project project = new Project(
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    userManager

            );

            Project project2 = new Project(
                    "Project para teste",
                    "Projeto para testar projeto",
                    endDate,
                    userManager

            );

            Project project3 = new Project(
                    "Outro nome de projeto",
                    "Projeto para testar projeto",
                    endDate,
                    userManager

            );

            List<Project> projectsFound = List.of(project, project2, project3);

            ProjectResponse projectResponse = new ProjectResponse(
                    projectId,
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    "gustavo"

            );

            ProjectResponse projectResponse2 = new ProjectResponse(
                    projectId2,
                    "Project para teste",
                    "Projeto para testar projeto",
                    endDate,
                    "gustavo"

            );

            ProjectResponse projectResponse3 = new ProjectResponse(
                    projectId3,
                    "Outro nome de projeto",
                    "Projeto para testar projeto",
                    endDate,
                    "gustavo"

            );


            List<ProjectResponse> projects = List.of(projectResponse, projectResponse2, projectResponse3);

            when(projectRepository.searchByName((nameToSearch))).thenReturn(projectsFound);

            when(projectMapper.toProjectResponse(any(Project.class)))
                    .thenReturn(projectResponse)
                    .thenReturn(projectResponse2)
                    .thenReturn(projectResponse3);

            List<ProjectResponse> output = projectService.getProjectsByName(nameToSearch);

            assertNotNull(output);
            assertFalse(output.isEmpty());
            assertEquals(projects.size(), output.size());

            assertEquals(projects, output);

            verify(projectRepository).searchByName(nameToSearch);

        }
    }

    @Nested
    class updateProject {

        @Test
        @DisplayName("Should update project with success")
        void shouldUpdateProject() throws ProjectNameTooShortException {

            var endDate = LocalDateTime.parse("2026-12-31T12:30:59");
            var endDateAtualizada = LocalDateTime.parse("2026-12-31T12:30:59");
            var projectId = 1L;

            var userManager = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            Project existingProject = new Project(
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    userManager

            );

            Project updatedProject = new Project(
                    "Projeto para teste atualizado",
                    "Projeto para testar projeto atualizado",
                    endDateAtualizada,
                    userManager
            );

            ProjectRequestUpdate projectRequestUpdate = new ProjectRequestUpdate(

                    "Projeto para teste atualizado",
                    "Projeto para testar projeto atualizado",
                    endDateAtualizada
            );

            ProjectResponse projectResponse = new ProjectResponse(
                    projectId,
                    "Projeto para teste atualizado",
                    "Projeto para testar projeto atualizado",
                    endDateAtualizada,
                    "gustavo"

            );


            when(projectRepository.findById((projectId))).thenReturn(Optional.of(existingProject));
            when(projectRepository.findByName(projectRequestUpdate.name())).thenReturn(Optional.empty());
            when(projectRepository.save(existingProject)).thenReturn(updatedProject);
            when(projectMapper.toProjectResponse(updatedProject)).thenReturn(projectResponse);

            var output = projectService.updateProject(projectId,projectRequestUpdate);

            assertNotNull(output);
            assertEquals(projectResponse, output);

            verify(projectRepository).findById(projectId);
            verify(projectRepository).findByName((projectRequestUpdate.name()));
            verify(projectRepository).save(existingProject);
            verify(projectMapper).toProjectResponse(updatedProject);

        }
    }

    @Nested
    class deleteProject {

        @Test
        @DisplayName("Should delete project with success")
        void shouldDeleteProjectWithSuccess() {

            var projectId = 1L;
            var endDate = LocalDateTime.parse("2026-12-31T12:30:59");

            var userFound = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            Project project = new Project(
                    "Projeto para teste",
                    "Projeto para testar projeto",
                    endDate,
                    userFound

            );

            when(projectRepository.findById((projectId))).thenReturn(Optional.of(project));

            projectService.deleteProject(projectId);

            verify(projectRepository).delete(project);
        }
    }
}