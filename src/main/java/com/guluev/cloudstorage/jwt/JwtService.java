package com.guluev.cloudstorage.jwt;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

public class JwtService {
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

}
