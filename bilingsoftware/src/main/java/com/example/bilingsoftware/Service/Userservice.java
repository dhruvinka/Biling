package com.example.bilingsoftware.Service;

import com.example.bilingsoftware.io.UserRequest;
import com.example.bilingsoftware.io.UserResponse;

import java.util.List;

public interface Userservice
{
  UserResponse createUser(UserRequest request);
   String getUserRole(String email);
   List<UserResponse> readUser();
   void deleteUser(String id);
}
