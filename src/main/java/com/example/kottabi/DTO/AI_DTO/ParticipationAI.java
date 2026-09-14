package com.example.kottabi.DTO.AI_DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationAI {

	private Double note;

	private String commentaire;

	private int classement;



	private String concourTitle;

}
