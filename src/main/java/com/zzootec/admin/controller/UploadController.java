package com.zzootec.admin.controller;

import com.zzootec.admin.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UploadController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/image")
    public String upload(@RequestParam MultipartFile file) {
        return cloudinaryService.uploadImage(file);
    }
}
