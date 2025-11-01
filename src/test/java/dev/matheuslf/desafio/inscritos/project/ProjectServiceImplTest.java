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