package com.guluev.cloudstorage.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionController {
    private final OperationRepository operationRepository;

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentials(BadCredentialsException e) {
        operationRepository.addExceptionOperation(e.getMessage());

        return new ResponseEntity<>(formAResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authorizationException(AuthenticationException e) {
        operationRepository.addExceptionOperation(e.getMessage());
        return new ResponseEntity<>(formAResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(ErrorInputDate.class)
    public ResponseEntity<?> ErrorInputDate(ErrorInputDate e) {
        operationRepository.addExceptionOperation(e.getMessage());
        return new ResponseEntity<>(formAResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> applicationException(ApplicationException e) {
        operationRepository.addExceptionOperation(e.getMessage());
        return new ResponseEntity<>(formAResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    public ExceptionResponse formAResponse(String exception) {
        return ExceptionResponse.builder().id(operationRepository.getCount()).message(exception).build();
    }

}
