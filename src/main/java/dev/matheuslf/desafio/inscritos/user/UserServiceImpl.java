package dev.matheuslf.desafio.inscritos.user;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest)
            throws UserNameTooShortException {

        log.info("Registrando um novo user {}", userRegisterRequest.userName());

        if (userRegisterRequest.userName().length() < 4){
            throw new UserNameTooShortException();
        }

        if (userRepository.existsByEmail(userRegisterRequest.email())) {
            throw new UserEmailDuplicateException();
        }

        User newUser = new User();
        newUser.setEmail(userRegisterRequest.email());
        newUser.setPassword(passwordEncoder.encode(userRegisterRequest.password()));
        newUser.setUserName(userRegisterRequest.userName());
        newUser.setRole(userRole());

        User userSalved = userRepository.save(newUser);

        log.info("Registrado um novo user {}", userSalved);

        return userMapper.toUserRegisterResponse(userSalved);

    }

    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);

        if (users.isEmpty()){
            return Page.empty();
        }

        return users.map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse findById(UUID id) {

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        log.debug("Retorna um usuário com id {} ", id);

        return userMapper.toUserResponse(user);
    }

    @Override
    public Optional<UserResponse> findByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()){

            System.out.println("Email não encontrado");
            return Optional.empty();
        }

        log.debug("Retorna um email com id {} ", user.get().getEmail());
        return user.map(userMapper::toUserResponse);
    }

    @Override
    public List<UserResponse> findByUsername(String username) {

        List<User> users = userRepository.searchByUserName(username);

        if (users.isEmpty()){

            log.debug("Nenhum usuário encontrado com o nome: {}", username);
            return Collections.emptyList();
        }
        log.debug("Retorna uma lista de usuarios com o nome pesquisado {} ", username);

        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserResponse updateUser(UUID id, UserRequestUpdate userRequestUpdate) {

        User userUpdate = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (userRequestUpdate.email() != null) {

            Optional<User> userEmail = userRepository.findByEmail(userRequestUpdate.email());

            if (userEmail.isPresent() && !userEmail.get().getId().equals(id)) {

                throw new UserEmailDuplicateException();
            }

            userUpdate.setEmail(userRequestUpdate.email());
        }

        if (userRequestUpdate.userName() != null) {
            userUpdate.setUserName(userRequestUpdate.userName());
        }

        if (userRequestUpdate.password() != null) {

            userUpdate.setPassword(passwordEncoder.encode(userRequestUpdate.password()));
        }

        log.info("Usuário atualizado com sucesso {}", userUpdate.getId());

        User userSalved = userRepository.save(userUpdate);

        return userMapper.toUserResponse(userSalved);
    }

    @Override
    public void deleteUser(UUID id) {

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);

        log.info("User {} deletado com sucesso", user.getUserName());
    }

    private UserRole userRole() {
        return userRepository.count() == 0 ? UserRole.MANAGER : UserRole.EMPLOYEE;
    }
}
