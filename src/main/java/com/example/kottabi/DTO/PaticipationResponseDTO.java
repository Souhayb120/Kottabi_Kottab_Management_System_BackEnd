package com.example.kottabi.DTO;

import com.example.kottabi.models.Concour;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaticipationResponseDTO {

	private Double note;
	private String commentaire;
	private int classement;

	private Concour concour;

	private Eleve eleve;

	private Enseignant enseignant;
}
