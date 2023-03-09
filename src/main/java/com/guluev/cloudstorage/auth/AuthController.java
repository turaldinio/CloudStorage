package com.guluev.cloudstorage.auth;

import com.guluev.cloudstorage.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final OperationService operationService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        var token = authenticationService.authenticate(request);
        operationService.addSuccessfulOperation(String.format("the user %s has successfully logged in", request.getLogin()));
        return ResponseEntity.ok(Map.of("auth-token", token));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        return authenticationService.logout();
    }

}