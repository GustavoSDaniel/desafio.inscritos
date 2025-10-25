package dev.matheuslf.desafio.inscritos.user;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserAuthorizationRoleService {

    public void validateRoleUser(UUID userId, Authentication authentication) {

        JWTUserData jwtUserData = (JWTUserData) authentication.getPrincipal();

        if ("MANAGER".equals(jwtUserData.role())) {
            return;
        }

        if ("EMPLOYEE".equals(jwtUserData.role()) && !jwtUserData.userId().equals(userId)) {
            throw new AccessDeniedException();
        }
    }
}
