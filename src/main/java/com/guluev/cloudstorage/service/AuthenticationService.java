package com.guluev.cloudstorage.service;

import com.guluev.cloudstorage.entity.User;
import com.guluev.cloudstorage.jwt.JwtService;
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

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder().
                email(request.getEmail()).
                password(passwordEncoder.encode(request.getPassword())).
                build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var a=authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(request.
                        getLogin(), request.getPassword())
                );

        var user = userRepository.findUserByEmail(request.getLogin()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}