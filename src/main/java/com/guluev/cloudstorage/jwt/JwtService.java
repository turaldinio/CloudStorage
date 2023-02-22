package com.guluev.cloudstorage.jwt;

public class JwtService {
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
