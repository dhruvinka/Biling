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
public class FileUploadServiceimp implements FileUploadService {

    private static final String UPLOAD_DIR = "uploads/";  // relative to project root
    private static final String IMAGE_BASE_URL = "http://localhost:8080/images/";

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileExtension = getFileExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID() + "." + fileExtension;

            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return IMAGE_BASE_URL + newFileName;  // 👈 return full URL
        } catch (IOException e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            // Extract just the filename from full URL
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
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
