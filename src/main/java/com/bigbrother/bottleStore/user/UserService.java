package com.bigbrother.bottleStore.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;

    private final UserMapper mapper;

    public User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User user) {
            return userRepository.findByUsername(user.getUsername());
        }
        return null;
    }


    public UserResponse saveUser(UserRequest request) {
        if(userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException("Username is already in use");
        }
        String password = encoder.encode(request.password());
        var user = mapper.toUser(request, password);
        userRepository.save(user);
        return mapper.toUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return null;
        }
        return users.stream().map(mapper::toUserResponse).collect(Collectors.toList());
    }
}
