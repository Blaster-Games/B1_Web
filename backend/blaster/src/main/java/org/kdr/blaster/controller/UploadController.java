package org.kdr.blaster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
@Log4j2
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/images")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("file") List<MultipartFile> files) {
        return ResponseEntity.ok(uploadService.uploadImages(files));
    }
}
