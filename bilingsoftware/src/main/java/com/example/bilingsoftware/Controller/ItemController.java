package com.example.bilingsoftware.Controller;


import com.example.bilingsoftware.Service.ItemService;
import com.example.bilingsoftware.io.ItemRequest;
import com.example.bilingsoftware.io.ItemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // Add new item with image
    @PostMapping("/admin/items")
    public ItemResponse addItem(
            @RequestPart("item") String itemString,
            @RequestPart("file") MultipartFile file
                               )
    {
        ObjectMapper objectMapper=new ObjectMapper();
        ItemRequest itemRequest=null;

                try
                {
                   itemRequest= objectMapper.readValue(itemString,ItemRequest.class);
                 return   itemService.add(itemRequest,file);

                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error while process the json");
                }


    }

    // Get all items
    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<ItemResponse> items = itemService.fetchItems();
        return ResponseEntity.ok(items);
    }

    // Delete item by itemId
    @DeleteMapping("/admin/items/{itemId}")
    public void deleteItem(@PathVariable String itemId) {
        try {

           itemService.deleteItem(itemId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can not delete the item");
        }

    }
}
