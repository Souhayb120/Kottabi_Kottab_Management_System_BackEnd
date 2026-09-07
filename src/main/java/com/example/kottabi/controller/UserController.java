package com.example.kottabi.controller;

import com.example.kottabi.DTO.AuthResponse;
import com.example.kottabi.DTO.ChangePasswordRequest;
import com.example.kottabi.DTO.UserAuthRequest;
import com.example.kottabi.DTO.UserLoginDTO;
import com.example.kottabi.config.AuthService;
import com.example.kottabi.services.UserPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

	private final AuthService authService;
	private final UserPasswordService userPasswordService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserAuthRequest request) {
		authService.register(request);
		return ResponseEntity.ok("User registered successfully");
	}

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
