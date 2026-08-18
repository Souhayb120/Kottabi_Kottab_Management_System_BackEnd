package com.example.kottabi.DTO;

import com.example.kottabi.models.Concour;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ParticipationRequestDTO {

	private Double note;
	private String commentaire;
	private int classement;

	private long concourId;

	private long eleveId;

	private long enseignantId;
}
