package com.live.messenger.auth;

import com.live.messenger.jwt.JwtService;
import com.live.messenger.login.LoginDto;
import com.live.messenger.user.Users;
import com.live.messenger.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for handling authentication-related logic.
 */
@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository; // Repository for accessing user data

    @Autowired
    private JwtService jwtService; // Service for handling JWT operations

    // Create an instance of BCryptPasswordEncoder for password hashing
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Authenticates the user and returns a JWT token if authentication is successful.
     *
     * @param loginDto the login credentials
     * @return a JWT token
     * @throws ResponseStatusException if the user is not found or the password is invalid
     */
    public String getJwt(LoginDto loginDto) {
        // Attempt to find the user by their username
        Users user = usersRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if the provided password matches the stored hashed password
        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        // If authentication is successful, create and return a JWT token for the user
        return jwtService.createJwt(user.getUsername(), user.getId());
    }
}
