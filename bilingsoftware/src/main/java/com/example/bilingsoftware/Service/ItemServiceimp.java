package com.example.bilingsoftware.Service;

import com.example.bilingsoftware.Repo.CategoryRepo;
import com.example.bilingsoftware.Repo.ItemRepo;
import com.example.bilingsoftware.entity.CategoryEntity;
import com.example.bilingsoftware.entity.ItemEntity;
import com.example.bilingsoftware.io.ItemRequest;
import com.example.bilingsoftware.io.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceimp implements ItemService {

    private final ItemRepo itemRepo;
    private final CategoryRepo categoryRepo;
    private final FileUploadService fileUploadService;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        ItemEntity newItem = convertToEntity(request);

        CategoryEntity existingCategory = categoryRepo.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        newItem.setCategory(existingCategory);
        newItem.setImgurl(imgUrl);
        newItem = itemRepo.save(newItem);

        return convertToResponse(newItem);
    }

    private ItemResponse convertToResponse(ItemEntity item) {
        return ItemResponse.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .imgurl(item.getImgurl())
                .categoryname(item.getCategory().getName())
                .categoryId(item.getCategory().getCategoryId())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepo.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem = itemRepo.findByItemId(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

       boolean isfiledelete= fileUploadService.deleteFile(existingItem.getImgurl());
      if (isfiledelete)
      {
          itemRepo.delete(existingItem);
      }
      else
      {
          throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Image can not delete form tha databse");
      }
    }
}
