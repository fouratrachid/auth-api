package com.fourat.authapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.
        UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.fourat.authapi.repositories.UserRepository ;

@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserRepository UserRepository ;
    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {
        return UserRepository.findByEmail(email).orElseThrow(()-> new
                UsernameNotFoundException("User not found !"));
    }
}