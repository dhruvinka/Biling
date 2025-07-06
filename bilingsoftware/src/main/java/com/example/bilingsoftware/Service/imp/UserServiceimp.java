package com.example.bilingsoftware.Service.imp;

import com.example.bilingsoftware.Repo.UserRepo;
import com.example.bilingsoftware.Service.Userservice;
import com.example.bilingsoftware.entity.UserEntity;
import com.example.bilingsoftware.io.UserRequest;
import com.example.bilingsoftware.io.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceimp  implements Userservice {

    private  final UserRepo userRepo;
    private  final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest request) {
      UserEntity newuser= convertToEntity(request);
    newuser=  userRepo.save(newuser);
    return convertToResponse(newuser);
    }

    private UserResponse convertToResponse(UserEntity newuser) {
        return UserResponse.builder()
                .userId(newuser.getUserId()) // ✅ use the actual saved userId
                .email(newuser.getEmail())
                .name(newuser.getName())
                .createdAt(newuser.getCreatedAt())
                .updatedAt(newuser.getUpdatedAt())
                .role(newuser.getRole()) // ✅ include role (optional but useful)
                .build();
    }


    private UserEntity convertToEntity(UserRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // ✅ Encode password here
                .role(request.getRole().toUpperCase())
                .name(request.getName())
                .build();
    }


    @Override
    public String getUserRole(String email) {

       UserEntity existingUser= userRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("user not found"+email));
       return existingUser.getRole();


    }

    @Override
    public List<UserResponse> readUser() {
      return   userRepo.findAll()
                .stream()
                .map(user->convertToResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
            UserEntity existingUser=    userRepo.findByUserId(id)
                        .orElseThrow(()->new UsernameNotFoundException("User not Found"));
                userRepo.delete(existingUser);

    }
}
