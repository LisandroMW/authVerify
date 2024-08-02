package com.Lisandro.authVerify.controller;

import com.Lisandro.authVerify.entity.User;
import com.Lisandro.authVerify.error.UserNotFoundException;
import com.Lisandro.authVerify.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("/findAllUsers")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("/saveUser")
    public User saveUser(@Valid @RequestBody User user) throws DataAccessException{
        return userService.saveUser(user);
    }

    @PutMapping("/updateUser/{id}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable Long id) throws UserNotFoundException, DataAccessException {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) throws UserNotFoundException, DataAccessException{
        userService.deleteUser(id);
        return "User was eliminated";
    }

    @GetMapping("/findUserById/{id}")
    public Optional<User> findUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.findUserById(id);
    }

    @GetMapping("/findUserByFirstName/{firstName}")
    public Optional<User> findUserByFirstName(@PathVariable String firstName) throws UserNotFoundException{
        return userService.findUserByFirstName(firstName);
    }

    @GetMapping("/findUsersByUsername/{username}")
    public List<Optional<User>> findUsersByUsername(@PathVariable String username){
        return userService.findUsersByUsername(username);
    }

    @GetMapping("/findUsersByEmail/{email}")
    public List<Optional<User>> findUsersByEmail(@PathVariable String email){
        return userService.findUsersByEmail(email);
    }
}
