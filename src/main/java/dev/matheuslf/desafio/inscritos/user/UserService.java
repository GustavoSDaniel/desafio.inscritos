package dev.matheuslf.desafio.inscritos.user;

public interface UserService {

    UserRegisterResponse  registerUser(UserRegisterRequest userRegisterRequest) throws UserNameTooShortException;

}
