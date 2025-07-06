package com.example.bilingsoftware.Controller;

import com.example.bilingsoftware.Service.Userservice;
import com.example.bilingsoftware.Service.imp.UserServiceimp;
import com.example.bilingsoftware.io.UserRequest;
import com.example.bilingsoftware.io.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private  final Userservice userservice;


    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest request)
    {

        try {
            return  userservice.createUser(request);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create a User");
        }

    }


    @GetMapping("/users")
    public List<UserResponse> readUser()
    {
        return  userservice.readUser();

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id)
    {
        try {
            userservice.deleteUser(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"we can't delete the user");
        }
    }





}
