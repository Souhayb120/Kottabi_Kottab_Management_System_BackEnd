package com.example.kottabi.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ParticipationRequestDTO {

	@NotBlank(message = "Note is required")
	private Double note;

	private String commentaire;

	private int classement;

	@Min(1)
	private long concourId;

	@Min(1)
	private long eleveId;

	@Min(1)
	private long enseignantId;
}
