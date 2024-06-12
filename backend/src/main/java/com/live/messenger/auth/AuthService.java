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
        Users user = usersRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return jwtService.createJwt(user.getUsername(), user.getId());
    }
}
