package com.guluev.cloudstorage.controllers;


import com.guluev.cloudstorage.model.FileResponse;
import com.guluev.cloudstorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {
    private final FileService userFileService;
    private final OperationService operationService;

    @GetMapping("/list")
    public List<FileResponse> getFileListLimit(Integer limit) {
        return userFileService.getFilesListLimit(limit);
    }


    @PostMapping(value = "/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> upload(MultipartFile file) {
        var result = userFileService.uploadFile(file);
        operationService.addSuccessfulOperation(String.format("the file %s is uploaded", file.getOriginalFilename()));
        return result;
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(String filename) {
        userFileService.deleteFile(filename);
        operationService.addSuccessfulOperation(String.format("the file %s is deleted", filename));
        return ResponseEntity.ok("Success deleted");
    }


    @PutMapping("/file")
    public ResponseEntity<?> updateFileName(String filename, @RequestBody FileResponse fileResponse) {
        userFileService.updateFileName(filename, fileResponse);
        operationService.addSuccessfulOperation(String.format("the file was renamed from %s to %s", filename, fileResponse.getFilename()));

        return ResponseEntity.ok("Success deleted");

    }

    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(String filename) {
        var result = userFileService.downloadFileByName(filename);

        operationService.addSuccessfulOperation(String.format("the %s file has been downloaded successfully", filename));

        return result;

    }


}
