package com.guluev.cloudstorage.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.function.Function;

public class JwtService {
    @Value("#{secret_key}")
    private static String SECRET_KEY;

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    

    private Key getSigningKey() {
        var currentKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(currentKey);
    }

}
