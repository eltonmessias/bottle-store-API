package com.bigbrother.bottleStore.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.ActiveProfiles;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByUsernameSuccess() {
        String username = "eltonmessias";
        UserRequest data = new UserRequest(
                UUID.randomUUID(),
                "eltonmessias",
                "messias8669",
                "Elton Guambe",
                "eltonmessias10@gmail.com",
                "844264898",
                ROLE.ADMIN
        );
        this.createUser(data);
        User result = this.userRepository.findByUsername(username);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(username);
    }


    private void createUser(UserRequest request) {
        User newUser = new User(request);
        this.entityManager.persist(newUser);
    }

}