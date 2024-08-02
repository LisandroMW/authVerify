package com.Lisandro.authVerify.service;

import com.Lisandro.authVerify.entity.User;
import com.Lisandro.authVerify.error.UserNotFoundException;
import com.Lisandro.authVerify.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .firstName("Martin")
                .username("Tincho")
                .email("martingomez@gmail.com")
                .build();
        user2 = User.builder()
                .id(2L)
                .firstName("Lisandro")
                .username("Lisu")
                .email("lisandrogomez@gmail.com")
                .build();


        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.save(any(User.class))).thenReturn(user1);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        when(userRepository.findUserByFirstNameIgnoreCaseContaining(any(String.class))).thenReturn(Optional.of(user1));
        when(userRepository.findUsersByUsernameIgnoreCaseContaining(any(String.class))).thenReturn(List.of(Optional.of(user1), Optional.of(user2)));
        when(userRepository.findUsersByEmailIgnoreCaseContaining(any(String.class))).thenReturn(List.of(Optional.of(user1), Optional.of(user2)));

    }

    @DisplayName("Test finding all users")
    @Test
    public void findAllUsers(){
        List<User> users = userService.findAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @DisplayName("Test saving a user")
    @Test
    public void saveUser() throws DataAccessException {
        User userSaved = userService.saveUser(user1);
        assertEquals(userSaved, user1);
    }

    @DisplayName("Test saving a user throws DataAccessException ")
    @Test
    public void saveUserThrowsDataAccessException(){
        User user = new User();
        when(userRepository.save(user)).thenThrow(new DataAccessException(""){});
        assertThrows(DataAccessException.class, ()->userService.saveUser(user));
    }

    @DisplayName("Test updating a user")
    @Test
    public void updateUser() throws UserNotFoundException, DataAccessException {
        User updatedUser = userService.updateUser(user1, 1L);
        assertEquals(user1, updatedUser);
    }

    @DisplayName("Test updating a user throws UserNoFoundException")
    @Test
    public void updateUserThrowUserNotFoundException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User updatedUser = new User(1L, "Jannet", "Jane Doe", "jane@example.com");

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(updatedUser, 1L);
        });
    }

    @DisplayName("Test deleting a user")
    @Test
    public void deleteUser() throws UserNotFoundException {
        Long userId = 1L;
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @DisplayName("Test deleting a user throws DataAccessException")
    @Test
    public void deleteUserException() throws UserNotFoundException, DataAccessException {
        doThrow(new DataAccessException("") {}).when(userRepository).deleteById(anyLong());
        assertThrows(DataAccessException.class, () -> userService.deleteUser(1L));
    }

    @DisplayName("Test finding a user by ID")
    @Test
    public void findUserById() throws UserNotFoundException {
        Optional<User> foundUser = userService.findUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals(user1, foundUser.get());
    }

    @DisplayName("Test finding a non-existent user by ID throws UserNotFoundException")
    @Test
    public void findUserByIdThrowUserNotFoundException() {

        long nonExistentUserId = 999L;
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(nonExistentUserId));
    }

    @DisplayName("Test finding a user by first name")
    @Test
    public void findUserByFirstName() throws UserNotFoundException {
        Optional<User> foundUser = userService.findUserByFirstName("Martin");
        assertTrue(foundUser.isPresent());
        assertEquals(user1, foundUser.get());
    }

    @DisplayName("Test finding a non-existent user by first name throws UserNotFoundException")
    @Test
    public void findUserByFirstNameThrowUserNotFoundException(){
        String nonExistentUserFirstName = "Juan";
        when(userRepository.findUserByFirstNameIgnoreCaseContaining(nonExistentUserFirstName)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, ()-> userService.findUserByFirstName(nonExistentUserFirstName));
    }

    @DisplayName("Test finding users by username")
    @Test
    public void findUsersByUsername() {
        List<Optional<User>> result = userService.findUsersByUsername("tin");
        assertEquals(2, result.size());
        assertTrue(result.contains(Optional.of(user1)));
        assertTrue(result.contains(Optional.of(user2)));
    }

    @DisplayName("Test finding users by email")
    @Test
    public void findUsersByEmail() {
        List<Optional<User>> result = userService.findUsersByEmail("gmail");
        assertEquals(2, result.size());
        assertTrue(result.contains(Optional.of(user1)));
        assertTrue(result.contains(Optional.of(user2)));
    }
}
