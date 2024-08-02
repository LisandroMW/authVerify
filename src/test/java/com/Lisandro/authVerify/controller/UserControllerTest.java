package com.Lisandro.authVerify.controller;

import com.Lisandro.authVerify.entity.User;
import com.Lisandro.authVerify.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userservice;

    private User user1;
    private User user2;

    

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .firstName("Thomas")
                .username("Tommy")
                .email("thomasturbado@gmail.com")
                .build();

        user2 = User.builder()
                .id(2L)
                .firstName("Lucas")
                .username("Lommy")
                .email("lucasmontiel@gmail.com")
                .build();


    }


    @Test
    public void findAllUsers() throws Exception{
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userservice.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/findAllUsers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void saveUser() throws Exception {
        User savedUser = User.builder()
                .firstName("Thomas")
                .username("Tommy")
                .email("thomasturbado@gmail.com")
                .build();
        when(userservice.saveUser(savedUser)).thenReturn(user1);
        mockMvc.perform(post("/saveUser").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"firstName\": \"Thomas\",\n" +
                        "   \"username\": \"Tommy\",\n" +
                        "   \"email\": \"thomasturbado@gmail.com\"" +
                        "\n" +
                        "}"))
                .andExpect(status().isOk());
    }


    @Test
    public void updateUser() throws Exception{
        when(userservice.updateUser(user1, 1L)).thenReturn(user1);
        mockMvc.perform(put("/updateUser/1").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "   \"firstName\": \"Thomas\",\n" +
                        "   \"username\": \"Tommy\",\n" +
                        "   \"email\": \"thomasturbado@gmail.com\"" +
                        "\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception{
        long userId = 1L;
        mockMvc.perform(delete("/deleteUser/1").content("User with Id " + userId + "has been deleted"))
                .andExpect(status().isOk());
    }

    @Test
    void findUserById() throws Exception{
        when(userservice.findUserById(1L)).thenReturn(Optional.of(user1));
        mockMvc.perform(get("/findUserById/1").contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user1.getFirstName()));
    }

    @Test
    void findUserByFirstName() throws Exception{
        when(userservice.findUserByFirstName("Thomas")).thenReturn(Optional.of(user1));
        mockMvc.perform(get("/findUserByFirstName/Thomas").contentType(MediaType.APPLICATION_JSON)
                .content("Thomas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user1.getUsername()));

    }

    @Test
    void findUsersByUsername() throws Exception {

        List<Optional<User>> users = new ArrayList<>();
        users.add(Optional.of(user1));
        users.add(Optional.of(user2));

        when(userservice.findUsersByUsername("mmy")).thenReturn(users);
        mockMvc.perform(get("/findUsersByUsername/mmy").contentType(MediaType.APPLICATION_JSON)
                .content("mmy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Thomas"))
                .andExpect(jsonPath("$[0].username").value("Tommy"))
                .andExpect(jsonPath("$[0].email").value("thomasturbado@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Lucas"))
                .andExpect(jsonPath("$[1].username").value("Lommy"))
                .andExpect(jsonPath("$[1].email").value("lucasmontiel@gmail.com"));
    }

    @Test
    void findUsersByEmail() throws Exception{

        List<Optional<User>> users = new ArrayList<>();
        users.add(Optional.of(user1));
        users.add(Optional.of(user2));

        when(userservice.findUsersByEmail("gmail.com")).thenReturn(users);
        mockMvc.perform(get("/findUsersByEmail/gmail.com").contentType(MediaType.APPLICATION_JSON)
                        .content("gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Thomas"))
                .andExpect(jsonPath("$[0].username").value("Tommy"))
                .andExpect(jsonPath("$[0].email").value("thomasturbado@gmail.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Lucas"))
                .andExpect(jsonPath("$[1].username").value("Lommy"))
                .andExpect(jsonPath("$[1].email").value("lucasmontiel@gmail.com"));
    }
}