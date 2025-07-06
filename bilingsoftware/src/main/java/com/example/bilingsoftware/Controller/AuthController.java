package com.example.bilingsoftware.Controller;

import com.example.bilingsoftware.Service.UserDetailService;
import com.example.bilingsoftware.Service.Userservice;
import com.example.bilingsoftware.Service.imp.AppUserDetailService;
import com.example.bilingsoftware.entity.UserEntity;
import com.example.bilingsoftware.io.AuthRequest;
import com.example.bilingsoftware.io.AuthResponse;
import com.example.bilingsoftware.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {


    private  final Userservice userservice;
    private  final JwtUtil jwtUtil;

    private  final  AppUserDetailService appUserDetailServicea;

    private  final AuthenticationManager authenticationManager;

    private  final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) throws Exception {
        authenticate(request.getEmail(), request.getPassword());

        final UserDetails userDetails = appUserDetailServicea.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtil.genrateToken(userDetails);
        String role = userservice.getUserRole(request.getEmail());

        return new AuthResponse(request.getEmail(), jwtToken, role);
    }


    private void authenticate(String email, String password) throws Exception {
       try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

       } catch (BadCredentialsException e)
       {
           throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email or password is incorrect");
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }


    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String,String> request)
    {
        return passwordEncoder.encode(request.get("password"));
    }



}
