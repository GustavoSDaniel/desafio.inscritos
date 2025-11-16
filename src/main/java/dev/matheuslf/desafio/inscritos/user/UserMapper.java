package dev.matheuslf.desafio.inscritos.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(UserRegisterRequest userRegisterRequest) {

        if (userRegisterRequest == null) {
            return null;
        }

        return new User(
                userRegisterRequest.email(),
                passwordEncoder.encode(userRegisterRequest.password()),
                userRegisterRequest.userName());
    }

    public UserRegisterResponse toUserRegisterResponse(User user){
        if(user == null){
            return null;
        }

        return new UserRegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getUserName(),
                user.getRole()
        );
    }

    public UserResponse toUserResponse(User user){
        if(user == null){
            return null;
        }

        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
