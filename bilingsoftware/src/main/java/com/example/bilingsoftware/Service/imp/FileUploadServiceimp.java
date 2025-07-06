package com.example.bilingsoftware.Service.imp;

import com.example.bilingsoftware.Service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceimp implements FileUploadService

{
    private static final String UPLOAD_DIR = "uploads/";  // relative to project root

    @Override
    public String uploadFile(MultipartFile file) {

        try {
        // Create the directory if it doesn't exist
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique file name
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID() + "." + fileExtension;

        // Full path to save the file
        Path filePath = uploadPath.resolve(newFileName);

        // Save the file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    } catch (IOException e) {
        throw new RuntimeException("Could not store file. Error: " + e.getMessage());
    }
}

@Override
public boolean deleteFile(String fileName) {
    try {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        return Files.deleteIfExists(filePath);
    } catch (IOException e) {
        return false;
    }
}

private String getFileExtension(String filename) {
    return filename.substring(filename.lastIndexOf(".") + 1);
}

}
