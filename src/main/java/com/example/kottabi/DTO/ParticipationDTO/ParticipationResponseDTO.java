package com.example.kottabi.DTO.ParticipationDTO;

import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
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
public class ParticipationResponseDTO implements Serializable {

	private long id;
	private Double note;
	private String commentaire;
	private int classement;

	private ConcourResponseDTO concour;

	private EleveResponseDTO eleve;

	private EnseignantResponseDTO enseignant;
}
