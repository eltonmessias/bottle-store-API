package com.bigbrother.bottleStore.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;

    @Column(nullable = false)
    @Email
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private ROLE role;




}
