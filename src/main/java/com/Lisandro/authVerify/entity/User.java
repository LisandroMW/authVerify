package com.Lisandro.authVerify.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

enum Gender{
    MALE, FEMALE, OTHER
        }

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please add the first name")
    @Length(min = 3, max = 20, message = "The first name most be between 4 and 20 characters long")
    @Column(name = "firstName")
    private String firstName;


    @NotBlank(message = "Please add the name")
    @Length(min = 3, max = 40, message = "The username most be between 4 and 40 characters long")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Please add the email")
    @Length(min = 7, max = 100, message = "The email most be between 15 and 100 characters long")
    @Email(message = "Invalid email")
    @Column(name = "email")
    private String email;





    /*
    @NotBlank(message = "Please add the second name")
    @Length(max = 50)
    @Column(name = "secondName")
    private String secondName;

    @NotBlank(message = "Please add the first name")
    @Length(max = 50)
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Min(18)
    @Column(name = "age")
    private int age;

    @NotBlank(message = "please add the password")
    @Length(min = 4, max = 30)
    @Column(name = "password")
    private String password;

    @Pattern(regexp = "^\\+?\\d+$")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

     */
}
