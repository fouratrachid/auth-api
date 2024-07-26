package com.fourat.authapi.services;
import org.springframework.http.ResponseEntity;
import com.fourat.authapi.dto.LoginDto ;
import com.fourat.authapi.dto.RegisterDto ;
import com.fourat.authapi.models.Role;
import com.fourat.authapi.models.User;


public interface IUserService {
    String authenticate(LoginDto loginDto);
    ResponseEntity<?> register (RegisterDto registerDto);
    Role saveRole(Role role);
    User saverUser (User user) ;
}