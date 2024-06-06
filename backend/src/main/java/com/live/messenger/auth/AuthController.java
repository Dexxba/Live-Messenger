package com.live.messenger.auth;

import com.live.messenger.login.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        // Invoke the authService to obtain a JWT token
        String jwtToken = authService.getJwt(loginDto);

        // Create a ResponseEntity with a 200 OK status and the JWT token in the response body
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jwtToken);
    }
}
