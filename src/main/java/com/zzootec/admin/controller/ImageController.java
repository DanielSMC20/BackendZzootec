package com.zzootec.admin.controller;

import com.zzootec.admin.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return imageService.upload(file);
    }
}
