package com.bigbrother.bottleStore.auth;

import com.bigbrother.bottleStore.jwt.*;
import com.bigbrother.bottleStore.user.ROLE;
import com.bigbrother.bottleStore.user.User;
import com.bigbrother.bottleStore.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;



    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        var user = userRepository.findByUsername(request.username());
        revokeAllUserTokens(user);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken);
        return new AuthResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String token){
        var tokenObj = new Token();
        tokenObj.setUser(user);
        tokenObj.setToken(token);
        tokenObj.setExpired(false);
        tokenObj.setRevoked(false);
        tokenRepository.save(tokenObj);
    }

    private void revokeAllUserTokens(User user){
        var validTokens = tokenRepository.findAllByUser(user);
        for (var token: validTokens){
            token.setRevoked(true);
            token.setExpired(true);
        }
        tokenRepository.saveAll(validTokens);
    }

    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);
        var user = userRepository.findByUsername(username);
        if(!jwtService.validateToken(refreshToken, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String newAccessToken = jwtService.generateToken(user);
        tokenRepository.save(new Token(newAccessToken, user, TokenType.BEARER, false, false));

        return ResponseEntity.ok(generateTokens(user));
    }

    private AuthResponse generateTokens(User user){
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken);
    }

    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
