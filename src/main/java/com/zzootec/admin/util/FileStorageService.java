package com.zzootec.admin.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final Cloudinary cloudinary;
    private static final Set<String> ALLOWED_EXT = Set.of("jpg", "jpeg", "png", "webp");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String LOCAL_UPLOAD_DIR = "uploads";

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    /**
     * Sube imagen a Cloudinary o localmente si falla Cloudinary
     * @param file Archivo a subir
     * @param folder Carpeta en Cloudinary (products, users, brands, categories)
     * @return URL de la imagen
     */
    public String saveImage(MultipartFile file, String folder) {
        try {
            // Validaciones
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Archivo vacío");
            }

            String original = file.getOriginalFilename();
            if (original == null || !original.contains(".")) {
                throw new RuntimeException("El archivo no tiene extensión válida");
            }

            String ext = original.substring(original.lastIndexOf('.') + 1).toLowerCase();
            if (!ALLOWED_EXT.contains(ext)) {
                throw new RuntimeException("Extensión no permitida. Usa: jpg, jpeg, png, webp");
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                throw new RuntimeException("Archivo muy grande. Máximo 5MB");
            }

            // Intentar subir a Cloudinary primero
            try {
                return uploadToCloudinary(file, folder, ext);
            } catch (Exception e) {
                log.warn("Cloudinary no disponible, usando almacenamiento local: {}", e.getMessage());
                return uploadToLocal(file, folder, ext);
            }

        } catch (Exception e) {
            log.error("Error subiendo imagen: {}", e.getMessage());
            throw new RuntimeException("Error subiendo imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Sube a Cloudinary
     */
    private String uploadToCloudinary(MultipartFile file, String folder, String ext) throws IOException {
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID() + "." + ext);
        
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(
                    tempFile,
                    ObjectUtils.asMap(
                            "folder", "zzootec/" + folder,
                            "resource_type", "auto",
                            "quality", "auto",
                            "fetch_format", "auto"
                    )
            );

            String cloudinaryUrl = (String) uploadResult.get("secure_url");
            log.info("Imagen subida a Cloudinary: {}", cloudinaryUrl);
            return cloudinaryUrl;
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * Sube localmente (fallback)
     */
    private String uploadToLocal(MultipartFile file, String folder, String ext) throws IOException {
        String filename = UUID.randomUUID() + "." + ext;
        Path dirPath = Paths.get(LOCAL_UPLOAD_DIR, folder);
        Files.createDirectories(dirPath);

        Path filePath = dirPath.resolve(filename);
        Files.write(filePath, file.getBytes());

        String localPath = folder + "/" + filename;
        log.info("Imagen subida localmente: {}", localPath);
        return localPath;
    }

    /**
     * Elimina imagen
     */
    public void deleteIfExists(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        try {
            // Si es URL de Cloudinary
            if (imageUrl.contains("cloudinary")) {
                String[] parts = imageUrl.split("/");
                String publicId = "zzootec/" + parts[parts.length - 2] + "/" + parts[parts.length - 1].split("\\.")[0];
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                log.info("Imagen eliminada de Cloudinary: {}", publicId);
            } else {
                // Si es ruta local
                Path path = Paths.get(LOCAL_UPLOAD_DIR, imageUrl);
                Files.deleteIfExists(path);
                log.info("Imagen eliminada localmente: {}", imageUrl);
            }
        } catch (Exception e) {
            log.warn("Error eliminando imagen: {}", e.getMessage());
        }
    }
}
