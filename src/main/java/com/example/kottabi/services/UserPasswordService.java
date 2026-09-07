package com.example.kottabi.services;


import com.example.kottabi.DTO.ChangePasswordRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserPasswordService {
    void changerLeMotDePasse(ChangePasswordRequest passwordRequest);
}
