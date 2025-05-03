package com.bigbrother.bottleStore.auth;

import com.bigbrother.bottleStore.jwt.JwtResponse;
import com.bigbrother.bottleStore.jwt.TokenRefreshRequestDTO;
import com.bigbrother.bottleStore.user.*;
import com.bigbrother.bottleStore.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            if(authentication.isAuthenticated()){
                User user = userRepository.findByUsername(request.username());
                ROLE role = user.getRole();

                String accessToken = jwtService.generateToken(request.username(), role);
                String refreshToken = jwtService.generateRefreshToken(request.username());
                return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, "Login Successfuly"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(e.getMessage(), "Invalid username or password"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody TokenRefreshRequestDTO request) {
        return ResponseEntity.ok(authService.refreshAccessToken(request.refreshToken()));
    }

    @GetMapping("/username")
    private Map<String, String> getLoggedInUsername() {
        return Map.of("username", authService.getLoggedInUsername());
    }
    



    record AuthResponse(String token, String message) {}


}
