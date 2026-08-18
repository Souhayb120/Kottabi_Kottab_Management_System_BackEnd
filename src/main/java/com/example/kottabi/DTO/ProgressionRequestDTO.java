package com.example.kottabi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgressionRequestDTO {

	private String sourat;
	private int versetDebut;
	private int versetFin;

	private long eleveId;

	private long enseignantId;
}
