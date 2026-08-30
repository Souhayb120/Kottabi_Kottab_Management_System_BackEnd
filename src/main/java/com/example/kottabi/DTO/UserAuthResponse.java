package com.example.kottabi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
    private String userName;
    private String nom;
    private String prenom;
    private String tel;


}
