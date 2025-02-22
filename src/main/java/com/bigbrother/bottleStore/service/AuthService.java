package com.bigbrother.bottleStore.service;

import com.bigbrother.bottleStore.dto.JwtResponse;
import com.bigbrother.bottleStore.enums.ROLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    JwtService jwtService;


    public JwtResponse refreshAccessToken(String token) {
        if(jwtService.isTokenExpired(token)) {
            throw new RuntimeException("Token is expired");
        }
        String username = jwtService.extractUsername(token);
        ROLE role = jwtService.extractRole(token);
        String newAccessToken = jwtService.generateToken(username, role);
        String newRefreshToken = jwtService.generateRefreshToken(username);
        return new JwtResponse(newAccessToken, newRefreshToken);
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
