package com.bigbrother.bottleStore.auth;

import com.bigbrother.bottleStore.jwt.JwtService;
import com.bigbrother.bottleStore.jwt.Token;
import com.bigbrother.bottleStore.jwt.TokenRepository;
import com.bigbrother.bottleStore.jwt.TokenType;
import com.bigbrother.bottleStore.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @PostMapping("/api/auth/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userRepository.findByUsername(username).orElseThrow();
        if(!jwtService.validateToken(refreshToken, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = jwtService.generateToken(user);
        tokenRepository.save(new Token(newAccessToken, user, TokenType.BEARER, false, false));

        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
    }
}
