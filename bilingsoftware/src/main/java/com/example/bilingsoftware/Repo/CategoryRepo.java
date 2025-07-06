package com.example.bilingsoftware.Repo;

import com.example.bilingsoftware.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import  java.util.*;

public interface CategoryRepo  extends JpaRepository<CategoryEntity,Long>
{

    Optional<CategoryEntity> findByCategoryId(String categoryId);


}
