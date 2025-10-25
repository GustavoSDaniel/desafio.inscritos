package dev.matheuslf.desafio.inscritos.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserAuthorizationServiceRole userAuthorizationService;

    public UserController(UserService userService, UserAuthorizationServiceRole userAuthorizationService) {
        this.userService = userService;
        this.userAuthorizationService = userAuthorizationService;
    }

    @GetMapping()
    @Operation(summary = "Lista todos os usuários")
    public ResponseEntity<Page<UserResponse>> getUsers(
            @ParameterObject
            @PageableDefault(size = 20, sort = "userName", direction = Sort.Direction.ASC)
            Pageable pageable) {

        Page<UserResponse> users = userService.getUsers(pageable);

        return ResponseEntity.ok(users);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisa o usuário pelo ID")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {

        UserResponse user = userService.findById(id);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/email")
    @Operation(summary = "Pesquisa usuário através do email fornecido")
    public ResponseEntity<UserResponse> findByEmail(@RequestParam String email) {

        return userService.findByEmail(email)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/searchName")
    @Operation(summary = "Pesquisa uma lista de usuarios com esse nome")
    public ResponseEntity<List<UserResponse>> findByName(@RequestParam String name) {

        List<UserResponse> users = userService.findByUsername(name);

        return ResponseEntity.ok().body(users);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizando Usuário")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @RequestBody @Valid UserRequestUpdate userUpdate,
                                                   Authentication authentication) {

        userAuthorizationService.validateRoleUser(id, authentication);

        UserResponse user = userService.updateUser(id, userUpdate);

        return ResponseEntity.ok(user);

    }

    @PatchMapping("/{id}/role")
    @Operation(summary = "Atualiza a role do usuário")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable UUID id,
            @RequestBody @Valid UserRequestUpdateRole updateRole){

        UserResponse user = userService.updateUserRole(id, updateRole);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletando usuario")
    ResponseEntity<Void> deleteUser(@PathVariable UUID id, Authentication authentication) {

        userAuthorizationService.validateRoleUser(id, authentication);

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
