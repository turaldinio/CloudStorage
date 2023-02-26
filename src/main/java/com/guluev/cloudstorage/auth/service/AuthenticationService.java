package com.guluev.cloudstorage.auth.service;

import com.guluev.cloudstorage.auth.AuthenticationToken;
import com.guluev.cloudstorage.auth.jwt.JwtService;
import com.guluev.cloudstorage.entity.User;
import com.guluev.cloudstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    

    public AuthenticationToken authenticate(AuthenticationRequest request) {
        var a=authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(request.
                        getLogin(), request.getPassword())
                );

        var user = userRepository.findUserByEmail(request.getLogin()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationToken.builder().token(jwtToken).build();
    }


}
