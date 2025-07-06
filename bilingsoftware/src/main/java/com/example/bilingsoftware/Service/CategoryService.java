package com.example.bilingsoftware.Service;

import com.example.bilingsoftware.io.CategoryRequest;
import com.example.bilingsoftware.io.CategoryResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface CategoryService {
     CategoryResponse add(CategoryRequest categoryRequest, MultipartFile file);


     List<CategoryResponse> read();

     void  delete(String categoryId);

}
