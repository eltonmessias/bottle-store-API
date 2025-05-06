package com.bigbrother.bottleStore.jwt;

import com.bigbrother.bottleStore.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token> findAllByUser(User user);
    Optional<Token> findByToken(String token);
}
