package com.example.kottabi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse implements Serializable {
    private String username;
    private String nom;
    private String prenom;
    private String email;
    private String tel;


}
