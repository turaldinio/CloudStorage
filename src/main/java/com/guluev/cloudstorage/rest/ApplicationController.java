package com.guluev.cloudstorage.rest;

import com.guluev.cloudstorage.auth.AuthenticationRequest;
import com.guluev.cloudstorage.auth.service.AuthenticationService;
import com.guluev.cloudstorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final AuthenticationService authenticationService;

    private final FileService fileService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ResponseEntity.ok(Map.of("auth-token", result.getToken()));

    }

    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(MultipartFile file) {
        fileService.uploadFile(file);
        return ResponseEntity.ok("asd");
    }
}
