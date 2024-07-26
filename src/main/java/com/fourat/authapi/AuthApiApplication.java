package com.fourat.authapi;

import com.fourat.authapi.models.Role;
import com.fourat.authapi.models.RoleName;
import com.fourat.authapi.repositories.RoleRepository;
import com.fourat.authapi.repositories.UserRepository;
import com.fourat.authapi.services.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApiApplication.class, args);
    }
    @Bean
    CommandLineRunner run (IUserService iUserService , RoleRepository
            roleRepository , UserRepository userRepository , PasswordEncoder
                                   passwordEncoder)
    {return args ->
    { iUserService.saveRole(new Role(RoleName.USER));
        iUserService.saveRole(new Role(RoleName.ADMIN));
    };}

}
