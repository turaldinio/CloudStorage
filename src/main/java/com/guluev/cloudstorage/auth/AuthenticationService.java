package com.guluev.cloudstorage.auth;

import com.guluev.cloudstorage.service.JwtService;
import com.guluev.cloudstorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final IAuthenticationImpl authentication;


    public String authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(request.
                        getLogin(), request.getPassword())
                );


        var user = userService.findUserByEmail(request.getLogin());

        var jwtToken = jwtService.generateToken(user);

        user.setUserToken(jwtToken);

        userService.saveAndFlush(user);


        return user.getUserToken();
    }

    public ResponseEntity<?> logout() {
        var user = authentication.getAuthenticationUser();
        user.setUserToken(null);
        userService.saveAndFlush(user);

        return ResponseEntity.ok("Success logout");
    }

}
