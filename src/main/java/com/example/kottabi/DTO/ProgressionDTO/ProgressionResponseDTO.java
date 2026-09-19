package com.example.kottabi.DTO.ProgressionDTO;

import com.example.kottabi.DTO.EleveDTO.EleveResponseDTO;
import com.example.kottabi.DTO.EnseignantDTO.EnseignantResponseDTO;
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

	private long id;
	private String sourat;
	private int versetDebut;
	private int versetFin;

	private EleveResponseDTO eleve;

	private EnseignantResponseDTO enseignant;
}
