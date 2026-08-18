package com.example.kottabi.DTO;

import com.example.kottabi.enums.Role;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EleveResponseDTO {

	private String userName;
	private String nom;
	private String prenom;
	private String tel;
	private LocalDate dateNaissance;
}
