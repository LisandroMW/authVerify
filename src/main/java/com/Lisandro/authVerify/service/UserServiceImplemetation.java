package com.Lisandro.authVerify.service;

import com.Lisandro.authVerify.entity.User;
import com.Lisandro.authVerify.error.UserNotFoundException;
import com.Lisandro.authVerify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImplemetation implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) throws DataAccessException {

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long id) throws DataAccessException, UserNotFoundException{

        Optional<User> existingUser = userRepository.findById(id);
        if(!existingUser.isPresent()){
            throw new UserNotFoundException("User isn't available");
        }

        User userDB = existingUser.get();

        if(Objects.nonNull(user.getFirstName()) && !"".equalsIgnoreCase(user.getUsername())){
            userDB.setUsername(user.getUsername());
        }
        if(Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())){
            userDB.setUsername(user.getUsername());
        }
        if(Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())){
            userDB.setEmail(user.getEmail());
        }
        return userRepository.save(userDB);
    }


    @Override
    public void deleteUser(Long id) throws DataAccessException, UserNotFoundException {

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("It's user doesn't exists");
        }else{
            userRepository.deleteById(id);
        }
    }



    @Override
    public Optional<User> findUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User isn't available");
        }
        return user;
    }

    @Override
    public Optional<User> findUserByFirstName(String firstName) throws UserNotFoundException {

        Optional<User> user = userRepository.findUserByFirstNameIgnoreCaseContaining(firstName);
        if(!user.isPresent()){
            throw new UserNotFoundException("User isn't available");
        }

        return user;
    }

    @Override
    public List<Optional<User>> findUsersByUsername(String username){

        return userRepository.findUsersByUsernameIgnoreCaseContaining(username);
    }

    @Override
    public List<Optional<User>> findUsersByEmail(String email) {
        return userRepository.findUsersByEmailIgnoreCaseContaining(email);
    }

}
