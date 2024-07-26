package com.fourat.authapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fourat.authapi.dto.LoginDto ;
import com.fourat.authapi.dto.RegisterDto ;
import com.fourat.authapi.services.IUserService;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserService iUserService ;
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto
                                               registerDto)
    { return iUserService.register(registerDto);}
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginDto loginDto)
    { return iUserService.authenticate(loginDto);}
}