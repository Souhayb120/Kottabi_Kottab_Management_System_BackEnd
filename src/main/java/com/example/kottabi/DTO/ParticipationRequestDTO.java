package com.example.kottabi.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDTO {

	@NotNull(message = "Note is required")
	private Double note;

	private String commentaire;

	private int classement;

	@NotNull(message = "concourId is required")
	@Min(1)
	private long concourId;

	@NotNull(message = "eleveId is required")
	@Min(1)
	private long eleveId;

	@NotNull(message = "enseignantId is required")
	@Min(1)
	private long enseignantId;
}
