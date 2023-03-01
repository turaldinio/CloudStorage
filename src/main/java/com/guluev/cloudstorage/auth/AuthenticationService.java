package com.guluev.cloudstorage.auth;

import com.guluev.cloudstorage.service.JwtService;
import com.guluev.cloudstorage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationToken authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(request.
                        getLogin(), request.getPassword())
                );

        var user = userService.findUserByEmail(request.getLogin());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationToken.builder().token(jwtToken).build();
    }


}
