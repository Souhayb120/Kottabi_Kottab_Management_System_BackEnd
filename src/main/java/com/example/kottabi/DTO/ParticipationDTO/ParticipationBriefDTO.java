package com.example.kottabi.DTO.ParticipationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationBriefDTO implements Serializable {

	private long id;
	private Double note;
	private String commentaire;
	private int classement;

	private String eleveUsername;
	private String elevePrenom;
	private String eleveNom;
}