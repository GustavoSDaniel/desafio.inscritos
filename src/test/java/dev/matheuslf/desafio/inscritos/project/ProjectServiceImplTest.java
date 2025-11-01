package dev.matheuslf.desafio.inscritos.project;

import dev.matheuslf.desafio.inscritos.user.UserRepository;
import dev.matheuslf.desafio.inscritos.user.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

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

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    class createProject {

        @Test
        @DisplayName("Should create project with success")
        void shouldCreateProjectWithSuccess() {





        }
    }

}