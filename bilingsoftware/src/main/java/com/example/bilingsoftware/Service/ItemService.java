package com.example.bilingsoftware.Service;

import com.example.bilingsoftware.io.ItemRequest;
import com.example.bilingsoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemResponse add(ItemRequest request, MultipartFile file);

    List<ItemResponse> fetchItems();

    void  deleteItem(String itemId);
}
