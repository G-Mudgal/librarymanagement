package com.gaurang.librarymanagement.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gaurang.librarymanagement.entity.Book;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenHelper {

    private String secret = "gemsol";

    public String generateToken(UserDetails userDetails) {
        return JWT.create().withSubject(userDetails.getUsername()).withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .withClaim("role", userDetails.getAuthorities().stream().toList().get(0).getAuthority())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndGetUsername(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
