package com.bigbrother.bottleStore.auth;

import com.bigbrother.bottleStore.jwt.JwtResponse;
import com.bigbrother.bottleStore.jwt.Token;
import com.bigbrother.bottleStore.jwt.TokenRepository;
import com.bigbrother.bottleStore.user.ROLE;
import com.bigbrother.bottleStore.jwt.JwtService;
import com.bigbrother.bottleStore.user.User;
import com.bigbrother.bottleStore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
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


//    public JwtResponse refreshAccessToken(String token) {
//        if(jwtService.isTokenExpired(token)) {
//            throw new RuntimeException("Token is expired");
//        }
//        String username = jwtService.extractUsername(token);
//        ROLE role = jwtService.extractRole(token);
//        String newAccessToken = jwtService.generateToken(username, role);
//        String newRefreshToken = jwtService.generateRefreshToken(username);
//        return new JwtResponse(newAccessToken, newRefreshToken);
//    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        var user = userRepository.findByUsername(request.username()).orElseThrow();
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

    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
