package dev.matheuslf.desafio.inscritos.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

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
