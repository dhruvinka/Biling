package com.example.bilingsoftware.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String uploadFile(MultipartFile file);
     boolean deleteFile(String imgurl);
}
