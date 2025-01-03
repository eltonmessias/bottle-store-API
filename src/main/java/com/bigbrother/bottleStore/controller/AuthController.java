package com.bigbrother.bottleStore.controller;

import com.bigbrother.bottleStore.dto.UserDTO;
import com.bigbrother.bottleStore.enums.ROLE;
import com.bigbrother.bottleStore.model.User;
import com.bigbrother.bottleStore.repository.UserRepository;
import com.bigbrother.bottleStore.service.JwtService;
import com.bigbrother.bottleStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO credentials) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password()));

            if(authentication.isAuthenticated()){
                User user = userRepository.findByUsername(credentials.username());
                ROLE role = user.getRole();

                String token = jwtService.generateToken(credentials.username(), role);
                return ResponseEntity.ok(new AuthResponse(token, "Login Successfuly"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(e.getMessage(), "Invalid username or password"));
        }
    }



    record AuthResponse(String token, String message) {}


}
