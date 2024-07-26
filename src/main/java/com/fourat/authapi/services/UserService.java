package com.fourat.authapi.services;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.
        UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.fourat.authapi.dto.BearerToken;
import com.fourat.authapi.dto.LoginDto;
import com.fourat.authapi.dto.RegisterDto;
import com.fourat.authapi.models.Role;
import com.fourat.authapi.models.RoleName;
import com.fourat.authapi.models.User;
import com.fourat.authapi.repositories.RoleRepository;
import com.fourat.authapi.repositories.UserRepository;
import com.fourat.authapi.security.JwtUtilities;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final AuthenticationManager authenticationManager ;
    private final UserRepository userRepository ;
    private final RoleRepository roleRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtilities jwtUtilities ;
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
    @Override
    public User saverUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if(userRepository.existsByEmail(registerDto.getEmail()))
        { return new ResponseEntity<>("email is already taken !",
                HttpStatus.SEE_OTHER); }
 else
        { User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setPassword(passwordEncoder.encode(registerDto.
                    getPassword()));
            String myrole = "user";
            if (registerDto.getUserRole().equals("") || registerDto.
                    getUserRole().equals("user")) {
                myrole = "USER";
            }
            if (registerDto.getUserRole().equals("admin")) {
                myrole = "ADMIN";
            }
            Role role = roleRepository.findByRoleName(RoleName.valueOf
                    (myrole));
            user.setUserRole(registerDto.getUserRole());
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            String token = jwtUtilities.generateToken(registerDto.getEmail(
            ),Collections.singletonList(role.getRoleName()));
            return new ResponseEntity<>(new BearerToken(token , "Bearer "),HttpStatus.OK);
        }
    }
    @Override
    public String authenticate(LoginDto loginDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(authentication.getName()).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r-> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.
                getUsername(),rolesNames);
        return "User login successful! Token: " + token;
    }
}