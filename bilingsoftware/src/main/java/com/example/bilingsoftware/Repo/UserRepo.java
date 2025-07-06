package com.example.bilingsoftware.Repo;

import com.example.bilingsoftware.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Long> {
  Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserId(String userId);
}
