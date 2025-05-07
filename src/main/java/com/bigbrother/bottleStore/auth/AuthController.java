package com.bigbrother.bottleStore.auth;


import com.bigbrother.bottleStore.user.*;
import com.bigbrother.bottleStore.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/bigbrother/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        return new ResponseEntity<>(userService.saveUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return new ResponseEntity<>(authService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @GetMapping("/username")
    private Map<String, String> getLoggedInUsername() {
        return Map.of("username", authService.getLoggedInUsername());
    }
    





}
