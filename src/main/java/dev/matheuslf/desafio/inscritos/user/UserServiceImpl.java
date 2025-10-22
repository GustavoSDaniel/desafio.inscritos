package dev.matheuslf.desafio.inscritos.user;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private UserRole userRole() {
        return userRepository.count() == 0 ? UserRole.MANAGER : UserRole.EMPLOYEE;
    }
}
