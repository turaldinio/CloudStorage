package com.guluev.cloudstorage.jwt;

import io.jsonwebtoken.Claims;

public class JwtService {
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
