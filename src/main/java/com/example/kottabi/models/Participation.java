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

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "eleve_id", referencedColumnName = "eleve_id")
	private Eleve eleve;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "councour_id", referencedColumnName = "councour_id")
	private Councour councour;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "enseignant_id", referencedColumnName = "enseignant_id")
	private Enseignant enseignant;
}
