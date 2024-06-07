package com.taskmanagement.demo.entity;

import com.taskmanagement.demo.config.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    @NotNull(message = "Username is not null")
    @NotBlank(message = "Username is not blank")
    private String username;
    private String password;
    private String role;
    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;
    private Integer phoneNumber;
}
