package com.live.messenger.auth;

import com.live.messenger.login.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

/**
 * Controller for handling authentication requests.
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService; // Service for handling authentication logic

    /**
     * Endpoint for user login.
     * Handles POST requests to /auth/login and authenticates the user.
     *
     * @param loginDto the login credentials sent in the request body
     * @return a ResponseEntity containing the JWT token if authentication is successful,
     * or an error message if authentication fails
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            // Attempt to authenticate the user and obtain a JWT token
            String jwtToken = authService.getJwt(loginDto);

            // Return the JWT token in the response body
            return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
        } catch (ResponseStatusException e) {
            // If authentication fails, return the appropriate HTTP status and error message
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
}
