package com.example.kottabi.DTO;

import com.example.kottabi.models.Concour;
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
public class ParticipationResponseDTO implements Serializable {

	private Double note;
	private String commentaire;
	private int classement;

	private ConcourResponseDTO concour;

	private EleveResponseDTO eleve;

	private EnseignantResponseDTO enseignant;
}
