package com.Lisandro.authVerify.repository;

import com.Lisandro.authVerify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findUserById(Long id);

    Optional<User> findUserByFirstNameIgnoreCaseContaining(String firstName);
    List<Optional<User>> findUsersByUsernameIgnoreCaseContaining(String username);

    List<Optional<User>> findUsersByEmailIgnoreCaseContaining(String email);



}