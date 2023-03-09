package com.guluev.cloudstorage.service;

import com.guluev.cloudstorage.entity.UserFiles;
import com.guluev.cloudstorage.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class UserFileService {
    private final FileRepository fileRepository;
    private final UserService userService;
    private final IAuthenticationImpl authentication;


    @Transactional
    public ResponseEntity<?> uploadFile(MultipartFile multipartFile) {
        try {

            var currentUserId= authentication.getCurrentUserId();

            var userFile = UserFiles.builder().
                    fileName(multipartFile.getOriginalFilename()).
                    fileSize(multipartFile.getSize()).
                    file(multipartFile.getBytes()).
                    userId(currentUserId).
                    build();


            fileRepository.saveAndFlush(userFile);

            return ResponseEntity.ok("Success upload");

        } catch (FileSizeLimitExceededException e) {
            throw new ErrorInputDate("ErrorInputDate input data: The file weighs too much ");
        } catch (IOException e) {
            throw new ApplicationException("ApplicationException File upload error ");

        }

    }

    public List<FileResponse> getFilesListLimit(Integer limit) {
        var currentUserId= authentication.getCurrentUserId();
        return fileRepository.findAll().
                stream().
                filter(x -> x.getUserId() == currentUserId).
                limit(limit == null ?
                        fileRepository.findAll().size() : limit).
                map(x -> FileResponse.
                        builder().
                        size(x.getFileSize()).
                        filename(x.getFileName()).
                        build()).
                collect(Collectors.toList());

    }


    @Transactional
    public void deleteFile(String filename) {
        var currentUserId= authentication.getCurrentUserId();



        fileRepository.getByFileNameAndUserId(filename, currentUserId).orElseThrow(() ->
                new ErrorInputDate("ErrorInputDate input date :The file %s is not found"));

        var result = fileRepository.deleteByFileNameAndUserId(filename, currentUserId);

        if (result == 0) {
            throw new ApplicationException(("ApplicationException delete file"));
        }


    }

    public ResponseEntity<?> downloadFileByName(String filename) {
        var currentUserId= authentication.getCurrentUserId();


        var file = fileRepository.getByFileNameAndUserId(filename, currentUserId).orElseThrow(() ->
                new ErrorInputDate(String.format("ErrorInputDate input date: The file %s is not found ",
                        filename)));

        try {
            return ResponseEntity.ok(new ByteArrayResource(file.getFile()));
        } catch (Exception e) {
            throw new ApplicationException("ApplicationException upload file");
        }
    }

    @Transactional
    public void updateFileName(String oldName, FileResponse fileResponse) {
        var currentUserId= authentication.getCurrentUserId();


        String newName = fileResponse.getFilename();
        fileResponse.getByFileNameAndUserId(oldName, currentUserId).orElseThrow(() ->
                new ErrorInputDate(String.format("ErrorInputDate input date: file %s not found exception", oldName)));

        try {
            fileRepository.update(oldName, newName,currentUserId);
        } catch (Exception e) {
            throw new ApplicationException("ApplicationException update file");
        }

    }
}
