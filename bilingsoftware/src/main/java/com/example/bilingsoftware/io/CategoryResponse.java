package com.example.bilingsoftware.io;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CategoryResponse {

    private String categoryId;
    private String name;
    private String des;
    private String bgcolor;
    private String imgurl;
    private Timestamp createdAt;
    private  Timestamp updatedAt;
    private  Integer items;
}
