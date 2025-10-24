package dev.matheuslf.desafio.inscritos.config;


import dev.matheuslf.desafio.inscritos.user.JWTUserData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final UserDetailsService userDetailsService;

    public SecurityFilter(TokenConfig tokenConfig, UserDetailsService userDetailsService) {
        this.tokenConfig = tokenConfig;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        boolean isPublic = Arrays.stream(SecurityConfig.PUBLIC_WHITELIST)
                .anyMatch(publicPath -> path
                        .startsWith(publicPath.replace("/**", "")));

        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }


        String authorizationHeader = request.getHeader("Authorization");

        if (Strings.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            Optional<JWTUserData> optionalUser = tokenConfig.validateToken(token);

            if (optionalUser.isPresent()) {
                JWTUserData jwtUserData = optionalUser.get();

                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUserData.email());

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}

