package com.example.bilingsoftware.Controller;

import com.example.bilingsoftware.Service.CategoryService;
import com.example.bilingsoftware.Service.FileUploadService;
import com.example.bilingsoftware.io.CategoryRequest;
import com.example.bilingsoftware.io.CategoryResponse;
import com.example.bilingsoftware.io.ItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/category")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString,
                                        @RequestPart("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest categoryRequest;
        try {
            categoryRequest = objectMapper.readValue(categoryString, CategoryRequest.class);
            return categoryService.add(categoryRequest, file); // pass 'image'
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse category JSON", e);
        }

    }

    @GetMapping("/category")
    public List<CategoryResponse> featch()
    {
        return  categoryService.read();
    }

    @DeleteMapping("/admin/category/{id}")
    public void  delete(@PathVariable String id)
    {
        categoryService.delete(id);
    }

}
