package com.example.kottabi.DTO;

import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgressionResponseDTO implements Serializable {

	private String sourat;
	private int versetDebut;
	private int versetFin;

	private EleveResponseDTO eleve;

	private EnseignantResponseDTO enseignant;
}
