package com.bigbrother.bottleStore.user;

import lombok.RequiredArgsConstructor;
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


    public UserResponse saveUser(UserRequest request) {
        if(userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException("Username is already in use");
        }
        var user = mapper.toUser(request);
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
