package com.live.messenger.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 */
@Service
public class JwtService {

    // Secret key for signing and verifying JWT tokens
    private final String secret = "$2h@WzR9&pL#6XqW";

    /**
     * Creates a JWT token for the given user.
     *
     * @param userName the username to include in the token
     * @param id       the user ID to include in the token
     * @return the generated JWT token as a String
     */
    public String createJwt(String userName, Integer id) {
        long currentTime = System.currentTimeMillis(); // Current time
        long endTime = currentTime + 60L * 60L * 24L * 1000L; // Expiration time (1 day in milliseconds)

        return Jwts
                .builder()
                .setIssuer("CsBe") // Set the issuer of the JWT.
                .setIssuedAt(new Date(currentTime)) // Set the issuance date of the JWT.
                .setExpiration(new Date(endTime)) // Set the expiration date.
                .setSubject(userName) // Set the subject (user name) of the JWT.
                .claim("userId", id) // Add a custom claim ("userId") to the JWT.
                .signWith(SignatureAlgorithm.HS256, secret) // Sign the JWT using the provided secret key and HMAC-SHA-256 algorithm.
                .compact(); // Compact the JWT into a string.
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param jwt the JWT token
     * @return the username extracted from the token
     */
    public String getUserName(String jwt) {
        return Jwts
                .parser()
                .setSigningKey(secret) // Set the secret key for verifying the JWT.
                .parseClaimsJws(jwt) // Parse the JWT claims with the provided key.
                .getBody()
                .getSubject(); // Get the subject (user name) from the JWT's claims.
    }
}
