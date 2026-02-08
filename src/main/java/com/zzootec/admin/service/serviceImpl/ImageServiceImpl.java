package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.service.CloudinaryService;
import com.zzootec.admin.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final CloudinaryService cloudinaryService;

    @Override
    public String upload(MultipartFile file) {
        return cloudinaryService.uploadImage(file);
    }
}
