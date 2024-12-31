package com.bigbrother.bottleStore.model;

import com.bigbrother.bottleStore.enums.ROLE;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    @NotNull
    private String username;
    private String password;
    private String fullName;
    @Column(nullable = false)
    @Email
    private String email;
    private String phone;
    @Enumerated(EnumType.STRING)
    private ROLE role;
}
