package com.zzootec.admin.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.zzootec.admin.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) {

        try {
            Map result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", "zzootec/products",
                            "resource_type", "image"
                    )
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al subir imagen a Cloudinary");
        }
    }
}