package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dto.UserDTO;
import jakarta.persistence.EntityManager;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should find user by Document from DB.")
    void findUserByDocumentTest() {

        UserDTO userDTO = new UserDTO(
                "Aquiles",
                "Dias",
                "123",
                "aquiles@email.com",
                "11122233341",
                new BigDecimal(50), UserType.COMMON);

        createUser(userDTO);

        Optional<User> result = repository.findUserByDocument(userDTO.document());

        assertThat( result.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Should not get User when user not exists")
    void notFindUserByDocumentTest() {
        String doc = "11122233342";

        Optional<User> result = repository.findUserByDocument(doc);

        assertThat( result.isEmpty() ).isTrue();
    }

    private User createUser(UserDTO dto){
        User user = new User(dto);
        entityManager.persist(user);
        return user;
    }
}