package com.bigbrother.bottleStore.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name()
        );
    }

    public User toUser(UserRequest request, String password) {
        return User.builder()
                .id(request.id())
                .username(request.username())
                .password(password)
                .fullName(request.fullName())
                .email(request.email())
                .phone(request.phone())
                .role(request.role())
                .build();

    }

}
