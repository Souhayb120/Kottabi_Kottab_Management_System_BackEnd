package com.example.kottabi.DTO;

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
public class ProgressionResponseDTO {

	private String sourat;
	private int versetDebut;
	private int versetFin;

	private Eleve eleve;

	private Enseignant enseignant;
}
