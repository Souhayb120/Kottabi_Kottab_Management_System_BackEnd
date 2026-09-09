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
public class Participation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Double note;
	private String commentaire;
	private int classement;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "concour_id")
	private Concour concour;


	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "eleve_id")
	private Eleve eleve;


	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "enseignant_id")
	private Enseignant enseignant;
}
