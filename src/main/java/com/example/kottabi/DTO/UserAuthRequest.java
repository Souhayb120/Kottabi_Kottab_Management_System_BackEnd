package com.example.kottabi.DTO;

import com.example.kottabi.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {

	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Nom is required")
	private String nom;

	@NotBlank(message = "Prenom is required")
	private String prenom;

	@NotBlank(message = "Email is required")
	@Email
	private String email;

	private LocalDate dateNaissance;

	@NotBlank(message = "Tel is required")
	private String tel;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must contain at least 6 characters")
	private String password;

	@NotBlank(message = "Role is required")
	private Role role;

	private String specialite;
	private String description;
}
