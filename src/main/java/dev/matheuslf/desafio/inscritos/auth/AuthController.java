package dev.matheuslf.desafio.inscritos.auth;

import dev.matheuslf.desafio.inscritos.config.TokenConfig;
import dev.matheuslf.desafio.inscritos.user.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping("/login")
    @Operation(summary = "Fazendo login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginUserRequest loginUserRequest) {

        UsernamePasswordAuthenticationToken userAndPassword =
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.email(), loginUserRequest.password());

        Authentication authentication = authenticationManager.authenticate(userAndPassword);

        User user = (User) authentication.getPrincipal();

        String token = tokenConfig.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(
            @Valid @RequestBody UserRegisterRequest userRegisterRequest)
            throws UserNameTooShortException {

        UserRegisterResponse newUser = userService.registerUser(userRegisterRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.id()).toUri();

        return ResponseEntity.created(location).body(newUser);

    }

}
