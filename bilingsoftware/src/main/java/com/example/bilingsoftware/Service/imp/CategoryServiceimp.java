package com.example.bilingsoftware.Service.imp;

import com.example.bilingsoftware.Repo.CategoryRepo;
import com.example.bilingsoftware.Repo.ItemRepo;
import com.example.bilingsoftware.Service.CategoryService;
import com.example.bilingsoftware.Service.FileUploadService;
import com.example.bilingsoftware.entity.CategoryEntity;
import com.example.bilingsoftware.io.CategoryRequest;
import com.example.bilingsoftware.io.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceimp implements CategoryService {
   @Autowired
   private final CategoryRepo categoryRepo;

   private  final ItemRepo itemRepo;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public CategoryResponse add(CategoryRequest categoryRequest, MultipartFile file)
    {
      String imgurl=  fileUploadService.uploadFile(file);
        CategoryEntity newCategory=converttoEntity(categoryRequest);
        newCategory.setImgurl(imgurl);
        newCategory= categoryRepo.save(newCategory);
       return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {
       return categoryRepo.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity exsitingCategory = categoryRepo.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("CategoryId is not found"));

        String imageUrl = exsitingCategory.getImgurl();

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            fileUploadService.deleteFile(imageUrl);
        }

        categoryRepo.delete(exsitingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
         Integer itemscount=itemRepo.countByCategoryId(newCategory.getId());


   return   CategoryResponse.builder()
             .categoryId(newCategory.getCategoryId())
             .bgcolor(newCategory.getBgcolor())
             .des(newCategory.getDes())
             .name(newCategory.getName())
             .imgurl(newCategory.getImgurl())
             .createdAt(newCategory.getCreatedAt())
             .updatedAt(newCategory.getUpdatedAt())
             .items(itemscount)
             .build();


    }

    private CategoryEntity converttoEntity(CategoryRequest categoryRequest) {

       return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(categoryRequest.getName())
                .des(categoryRequest.getDes())
                .bgcolor(categoryRequest.getBgcolor())
                .build();
    }
}
