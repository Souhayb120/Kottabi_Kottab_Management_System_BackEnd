package com.example.kottabi.controller;

import com.example.kottabi.DTO.UserDTO.AuthResponse;
import com.example.kottabi.DTO.UserDTO.ChangePasswordRequest;
import com.example.kottabi.DTO.UserDTO.UserAuthRequest;
import com.example.kottabi.DTO.UserDTO.UserLoginDTO;
import com.example.kottabi.config.AuthService;
import com.example.kottabi.services.UserPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final UserPasswordService userPasswordService;



	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginDTO request) {
		AuthResponse response = authService.login(request);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/changePassword")
	public void changePassword(
            @RequestBody ChangePasswordRequest passwordRequest
            ) {
		userPasswordService.changerLeMotDePasse(passwordRequest);
	}
}
