package com.guluev.cloudstorage.service;

import com.guluev.cloudstorage.entity.UserFiles;
import com.guluev.cloudstorage.repository.FileRepository;
import com.guluev.cloudstorage.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;


    @Transactional
    public ResponseEntity<?> uploadFile(MultipartFile multipartFile) {
        try {

            var user = userRepository.findUserByEmail(
                    SecurityContextHolder.
                            getContext().
                            getAuthentication().
                            getName()
            ).orElseThrow();

            var userFile = UserFiles.builder().
                    fileName(multipartFile.getOriginalFilename()).
                    fileSize(multipartFile.getSize()).
                    file(multipartFile.getBytes()).
                    userId(user.getId()).
                    build();

            fileRepository.saveAndFlush(userFile);

            return ResponseEntity.ok("Success upload");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
