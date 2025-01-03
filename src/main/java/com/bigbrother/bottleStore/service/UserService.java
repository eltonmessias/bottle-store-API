package com.bigbrother.bottleStore.service;

import com.bigbrother.bottleStore.dto.UserDTO;
import com.bigbrother.bottleStore.model.User;
import com.bigbrother.bottleStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    private UserDTO convertToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getRole()
        );
    }

    public UserDTO saveUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.username())){
            throw new IllegalArgumentException("Username is already in use");
        }
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(encoder.encode(userDTO.password()));
        user.setEmail(userDTO.email());
        user.setFullName(userDTO.fullName());
        user.setPhone(userDTO.phone());
        user.setRole(userDTO.role());

        user = userRepository.save(user);
        return convertToUserDTO(user);
    }
}
