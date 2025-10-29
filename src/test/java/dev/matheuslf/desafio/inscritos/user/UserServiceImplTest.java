package dev.matheuslf.desafio.inscritos.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    //Arrange (preparação), act (ação), assert (Verificação)

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    class registerUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldRegisterUser() throws UserNameTooShortException {

            var input = new UserRegisterRequest(
                    "gustavo", "gustavo@gmail.com", "senhaforte");

            var user = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            var userResponse = new UserRegisterResponse(
                    UUID.randomUUID(),
                    "gustavo",
                    "gustavo@gmail.com",
                    UserRole.MANAGER

            );

            when(passwordEncoder.encode(input.password())).thenReturn("senha-encodada-fake");

            when(userRepository.save(any(User.class))).thenReturn(user);

            when(userMapper.toUserRegisterResponse(user)).thenReturn(userResponse);

            var output = userService.registerUser(input);

            assertNotNull(output);

            verify(passwordEncoder).encode("senhaforte");

            verify(userRepository).save(any(User.class));

            verify(userMapper).toUserRegisterResponse(user);

        }
    }

    @Nested
    class allUsers {

        @Test
        @DisplayName("Should allUsers with success")
        void shouldAllUsersWithSuccess(){

            Pageable pageable = Pageable.unpaged();

            var user = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            var user2 = new User(
                    "gustavo",
                    "gustavo2@gmail.com",
                    "senha-encodada-fake"
            );

            var user3 = new User(
                    "gustavo",
                    "gustavo3@gmail.com",
                    "senha-encodada-fake"
            );

            List<User> users = List.of(user, user2, user3);

            Page<User> userPage = new PageImpl<>(users, pageable, users.size());

            var userResponse = new UserResponse(
                    UUID.randomUUID(), "gustavo", "gustavo@gmail.com", UserRole.MANAGER);

            var userResponse2 = new UserResponse(
                    UUID.randomUUID(), "gustavo", "gustavo2@gmail.com", UserRole.EMPLOYEE);

            var userResponse3 = new UserResponse(
                    UUID.randomUUID(), "gustavo", "gustavo3@gmail.com", UserRole.EMPLOYEE);

            when(userRepository.findAll(pageable)).thenReturn(userPage);

            when(userMapper.toUserResponse(user)).thenReturn(userResponse);
            when(userMapper.toUserResponse(user2)).thenReturn(userResponse2);
            when(userMapper.toUserResponse(user3)).thenReturn(userResponse3);

            Page<UserResponse> output = userService.getUsers(pageable);

            assertNotNull(output);

            verify(userRepository).findAll(pageable);
            verify(userMapper, times(3)).toUserResponse(any(User.class));

        }
    }

    @Nested
    class findUserById {
        @Test
        @DisplayName("Should find user by id with success")
        void shouldFindUserById() {


            var userId = UUID.randomUUID();

            var userFound = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );



            var userResponse = new UserResponse(
                    userId,
                    "gustavo",
                    "gustavo@gmail.com",
                    UserRole.MANAGER

            );

            when(userRepository.findById((userId))).thenReturn(Optional.of(userFound));

            when(userMapper.toUserResponse(userFound)).thenReturn(userResponse);

            var output = userService.findById(userId);

            assertNotNull(output);
            assertEquals(userResponse, output);

            verify(userRepository).findById(userId);
            verify(userMapper).toUserResponse(userFound);

        }
    }

    @Nested
    class findUserByEmail {

        @Test
        @DisplayName("Should find user by email with success")
        void shouldFindUserByEmail() {

            var userEmail = "gustavo@gmail.com";

            var userFound = new User(
                    "gustavo",
                    userEmail,
                    "senha-encodada-fake"
            );

            var userResponse = new UserResponse(
                    UUID.randomUUID(),
                    "gustavo",
                    "gustavo@gmail.com",
                    UserRole.MANAGER

            );

            when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(userFound));

            when(userMapper.toUserResponse(userFound)).thenReturn(userResponse);

            var output = userService.findByEmail(userEmail);

            assertNotNull(output);
            assertTrue(output.isPresent());
            assertEquals(userResponse, output.get());

            verify(userRepository).findByEmail(userEmail);
            verify(userMapper).toUserResponse(userFound);
        }
    }

    @Nested
    class findByUsername {

        @Test
        @DisplayName("Should find user by name with success")
        void shouldFindUserByUsername() {

            var userName = "gustavo";

            var userFound = new User(
                    userName,
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            var userResponse = new UserResponse(
                    UUID.randomUUID(),
                    "gustavo",
                    "gustavo@gmail.com",
                    UserRole.MANAGER

            );

            List<User> userNames = List.of(userFound);

            when(userRepository.searchByUserName(userName)).thenReturn(userNames);

            when(userMapper.toUserResponse(userFound)).thenReturn(userResponse);

            List<UserResponse> output = userService.findByUsername(userName);

            assertNotNull(output);
            assertFalse(output.isEmpty());

            verify(userRepository).searchByUserName(userName);
            verify(userMapper).toUserResponse(userFound);
        }
    }

    @Nested
    class updateUser{

        @Test
        @DisplayName("Should update user by id with success")
        void shouldUpdateUser() {

            var userId = UUID.randomUUID();

            var userFound = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            var userUpdate = new UserRequestUpdate(

                    "gustavoUpdate",
                    "gustavoUpdate@gmail.com",
                    "senha-encodada-fak-update"
            );

            var userResponse = new UserResponse(
                    userId,
                    "gustavoUpdate",
                    "gustavoUpdate@gmail.com",
                    UserRole.EMPLOYEE
            );

            when(userRepository.findById((userId))).thenReturn(Optional.of(userFound));

            when(userRepository.findByEmail(userUpdate.email())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(userUpdate.password())).thenReturn(userUpdate.password());

            when(userRepository.save(any(User.class))).thenReturn(userFound);

            when(userMapper.toUserResponse(userFound)).thenReturn(userResponse);

            UserResponse output = userService.updateUser(userId, userUpdate);

            assertNotNull(output);
            assertEquals(userResponse, output);


            verify(userRepository).findById(userId);
            verify(userRepository).findByEmail(userUpdate.email());
            verify(passwordEncoder).encode(userUpdate.password());
            verify(userRepository).save(userFound);
            verify(userMapper).toUserResponse(userFound);

        }
    }

    @Nested
    class deleteUser{

        @Test
        @DisplayName("Should delete user with success")
        void shouldDeleteUser() {
            var userId = UUID.randomUUID();

            var userFound = new User(
                    "gustavo",
                    "gustavo@gmail.com",
                    "senha-encodada-fake"
            );

            when(userRepository.findById((userId))).thenReturn(Optional.of(userFound));

            userService.deleteUser(userId);

            verify(userRepository).delete(userFound);

        }
    }

}