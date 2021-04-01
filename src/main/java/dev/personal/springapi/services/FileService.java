package dev.personal.springapi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private final Path uploadsPath;
    private final String uploadsLocation;
    public FileService(@Value("${file.storage.location:temp}") String uploadsLocation) {
        uploadsPath = Paths.get(uploadsLocation).toAbsolutePath().normalize();
        this.uploadsLocation = uploadsLocation;
        try {
            Files.createDirectories(uploadsPath);
        } catch (IOException e) {
            throw new RuntimeException("Error creando la carpeta", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename() != null ? StringUtils.cleanPath(file.getOriginalFilename()) : "";
        Path filePath = Paths.get(String.format("%s\\%s", this.uploadsPath, fileName));
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error guardando el archivo", e);
        }

        return fileName;
    }

    public Resource downloadFile(String fileName) {
        Path path = Paths.get(this.uploadsLocation).toAbsolutePath().resolve(fileName);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error leyendo el archivo", e);
        }
        if (resource.exists() && resource.isReadable()) {
            return resource;
        }else {
            throw new RuntimeException("El archivo no existe o no es legible");
        }
    }
}
