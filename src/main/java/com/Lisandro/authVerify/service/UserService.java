package com.Lisandro.authVerify.service;

import com.Lisandro.authVerify.entity.User;
import com.Lisandro.authVerify.error.UserNotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<User> findAllUsers();
    User saveUser(User user) throws DataAccessException;
    User updateUser(User user, Long id) throws UserNotFoundException, DataAccessException;
    void deleteUser(Long id) throws UserNotFoundException, DataAccessException;


    Optional<User> findUserById(Long id) throws UserNotFoundException;

    Optional<User> findUserByFirstName(String firstName) throws UserNotFoundException;

    List<Optional<User>> findUsersByUsername(String username);

    List<Optional<User>> findUsersByEmail(String email);
}
