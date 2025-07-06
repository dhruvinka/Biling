package com.example.bilingsoftware.Service;

import com.example.bilingsoftware.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
