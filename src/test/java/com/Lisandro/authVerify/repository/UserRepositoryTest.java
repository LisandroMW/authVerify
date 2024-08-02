package com.Lisandro.authVerify.repository;

import com.Lisandro.authVerify.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager testEntityManager;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .firstName("Sebastian")
                .username("Sebita666")
                .email("sebastito@gmail.com")
                .build();
        user2 = User.builder()
                .firstName("Juan")
                .username("Juanito123")
                .email("juanito@example.com")
                .build();
        user3 = User.builder()
                .firstName("Maria")
                .username("Maria669")
                .email("maria@example.com")
                .build();

        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.persist(user3);
    }

    @Test
    public void findUserByIdFound(){
        Optional<User> auxUser = userRepository.findUserById(user1.getId());
        assertTrue(auxUser.isPresent());
        assertEquals(user1.getFirstName(), auxUser.get().getFirstName());
        assertEquals(user1.getUsername(), auxUser.get().getUsername());
        assertEquals(user1.getEmail(), auxUser.get().getEmail());
    }

    @Test
    public void findUserByIdNotFound(){
        long nonExistentUserId = 999L;
        Optional<User> auxUser = userRepository.findUserById(nonExistentUserId);
        assertTrue(auxUser.isEmpty());
    }

    @Test
    public void findUserByFirstNameIgnoreCaseContainingFound(){
        Optional<User> auxUser = userRepository.findUserByFirstNameIgnoreCaseContaining("Sebastian");
        assertEquals(auxUser.get().getFirstName(), "Sebastian");
        System.out.println("user = " + auxUser.get());
    }

    @Test
    public void findUserByFirstNameIgnoreCaseContainingNotFound(){
        Optional<User> auxUser = userRepository.findUserByFirstNameIgnoreCaseContaining("Martin");
        assertEquals(auxUser, Optional.empty());
        System.out.println("user = " + auxUser);
    }

    @Test
    public void findUsersByUsernameIgnoreCaseContaining() {
        List<Optional<User>> foundUsers = userRepository.findUsersByUsernameIgnoreCaseContaining("66");

        assertEquals(2, foundUsers.size());
        assertTrue(foundUsers.stream().anyMatch(u -> u.get().getUsername().equals("Sebita666")));
        assertTrue(foundUsers.stream().anyMatch(u -> u.get().getUsername().equals("Maria669")));
    }

    @Test
    public void findUsersByEmailIgnoreCaseContaining() {
        List<Optional<User>> foundUsers = userRepository.findUsersByEmailIgnoreCaseContaining("example");

        assertEquals(2, foundUsers.size());
        assertTrue(foundUsers.stream().anyMatch(u -> u.get().getEmail().equals("juanito@example.com")));
        assertTrue(foundUsers.stream().anyMatch(u -> u.get().getEmail().equals("maria@example.com")));
    }
}
