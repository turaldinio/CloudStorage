package com.guluev.cloudstorage.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authorize(AuthenticationRequest request) {
        var token = authenticationService.authenticate(request).getToken();
        return ResponseEntity.ok(Map.of("auth-token", token));

    }
}
