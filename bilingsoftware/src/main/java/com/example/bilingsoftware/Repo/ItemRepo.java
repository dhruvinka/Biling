package com.example.bilingsoftware.Repo;

import com.example.bilingsoftware.entity.CategoryEntity;
import com.example.bilingsoftware.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepo extends JpaRepository<ItemEntity, Long> {


 Optional<ItemEntity> findByItemId(String id);

  Integer countByCategoryId(Long id);

}
