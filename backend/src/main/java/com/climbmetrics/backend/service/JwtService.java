package com.climbmetrics.backend.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;



@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secret;


    private final long expirationTime =
            1000 * 60 * 60; // 1 hour



    /*
     * Creates the signing key used to sign/verify JWTs
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );

    }

    /*
     * Generate a JWT token for a user
     */
    public String generateToken(String email) {


        return Jwts.builder()

                // The user identity
                .subject(email)

                // When token was created
                .issuedAt(new Date())

                // When token expires
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expirationTime
                        )
                )

                // Sign the token
                .signWith(getSigningKey())

                // Convert to String
                .compact();

    }

    /*
     * Extract email from JWT
     */
    public String extractEmail(String token) {


        Claims claims =
                Jwts.parser()

                        .verifyWith(getSigningKey())

                        .build()

                        .parseSignedClaims(token)

                        .getPayload();


        return claims.getSubject();

    }

    /*
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {

        return getExpiration(token)
                .before(new Date());

    }

    /*
     * Check if token is valid
     */
    public boolean isTokenValid(
            String token,
            String email
    ) {


        String tokenEmail =
                extractEmail(token);


        return tokenEmail.equals(email)
                && !isTokenExpired(token);

    }

    private Date getExpiration(String token) {


        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload()

                .getExpiration();

    }

}
