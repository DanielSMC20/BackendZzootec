package com.zzootec.admin.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final String BASE_DIR = "uploads";
    private static final Set<String> ALLOWED_EXT = Set.of("jpg", "jpeg", "png", "webp");

    public String saveImage(MultipartFile file, String folder) {
        try {
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

            String filename = UUID.randomUUID() + "." + ext;

            Path dirPath = Paths.get(BASE_DIR, folder);
            Files.createDirectories(dirPath);

            Path filePath = dirPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Esto se guarda en BD: "products/xxxx.png" o "users/xxxx.jpg"
            return folder + "/" + filename;

        } catch (Exception e) {
            throw new RuntimeException("Error guardando imagen: " + e.getMessage(), e);
        }
    }

    public void deleteIfExists(String relativePath) {
        try {
            if (relativePath == null || relativePath.isBlank()) return;
            Path path = Paths.get(BASE_DIR, relativePath);
            Files.deleteIfExists(path);
        } catch (Exception ignored) {
            // En producción aquí iría log, no reventamos por borrar
        }
    }
}
