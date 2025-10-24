package dev.matheuslf.desafio.inscritos.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserRegisterResponse  registerUser(UserRegisterRequest userRegisterRequest)
            throws UserNameTooShortException;

    Page<UserResponse> getUsers(Pageable pageable);

    UserResponse findById(UUID id);

    Optional<UserResponse> findByEmail(String email);

    List<UserResponse> findByUsername(String username);

    UserResponse updateUser(UUID id, UserRequestUpdate userRequestUpdate);

    void deleteUser(UUID id);

}
