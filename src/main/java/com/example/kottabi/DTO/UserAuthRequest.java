package com.example.kottabi.DTO;

import com.example.kottabi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {
    private String userName;
    private String nom;
    private String prenom;
    private String tel;
    private String password;
    private Role role;
}
