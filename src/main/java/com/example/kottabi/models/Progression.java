package com.example.kottabi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Progression {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String sourat;
	private int versetDebut;
	private int versetFin;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "eleve_id")
	private Eleve eleve;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "enseignant_id", referencedColumnName = "enseignant_id")
	private Enseignant enseignant;
}
