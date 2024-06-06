package com.live.messenger.jwt;

import com.live.messenger.user.Users;
import com.live.messenger.user.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * The JwtFilter class is responsible for filtering and authenticating requests using JWT (JSON Web Token).
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsersRepository usersRepository;

    /**
     * Determines whether the filter should be applied to a given request based on the request URI.
     * The filter is not applied to certain URI patterns, such as user registration and authentication endpoints.
     *
     * @param request The HTTP request.
     * @return True if the filter should not be applied, false otherwise.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/users") ||
                request.getRequestURI().equals("/auth/login");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract the JWT from the Authorization header.
        String authorization = request.getHeader("Authorization");
        String jwt = authorization.substring("Bearer ".length());

        // Retrieve the username from the JWT.
        String userName = jwtService.getUserName(jwt);
        Optional<Users> users = usersRepository.findByUsername(userName);
        List<GrantedAuthority> authorities = List.of(()->"user");
        // Create an authentication token and set it in the security context.
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, null,authorities );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue the filter chain for authenticated users.
        filterChain.doFilter(request, response);
    }
}
