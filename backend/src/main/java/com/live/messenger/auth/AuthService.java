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


@Service
public class AuthService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtService jwtService;

    // Create an instance of BCryptPasswordEncoder for password hashing
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public String getJwt(LoginDto loginDto) {
        // Attempt to find a user by their username, or throw an exception with status NOT_FOUND
        Users user = usersRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Check if the provided password matches the hashed password stored in the database
        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            // If the password doesn't match, throw an exception with status UNAUTHORIZED
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // If the username and password are valid, create and return a JWT for the user
        return jwtService.createJwt(user.getUsername(),user.getId());
    }
}
