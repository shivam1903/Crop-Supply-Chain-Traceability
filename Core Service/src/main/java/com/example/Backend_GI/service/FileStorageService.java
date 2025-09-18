package com.example.Backend_GI.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = System.getProperty("user.dir") + "/uploads";


    public FileStorageService() {

        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Upload directory created at: " + path.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException("Could not create upload directory", e);
            }
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot save empty file");
        }

        // Generate a unique file name
        String fileName = file.getOriginalFilename();

        // Save the file to the upload directory
        Path filePath = Paths.get(uploadDir, fileName);
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }
}

