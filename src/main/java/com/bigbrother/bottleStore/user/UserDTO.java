package com.bigbrother.bottleStore.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO (
        Long id,
        @NotNull(message = "The username is necessary")
        @NotBlank(message = "THe user name cannot be blank")
        String username,
        String password,
        String fullName,
        @NotNull
        @Email(message = "Enter a valid email")
        String email,
        String phone,
        @NotNull(message = "Role is required")
        ROLE role
){
}
