package com.example.kottabi.DTO.AI_DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiRapportResponceDTO {

	private String resume;
	private String pointsForts;
	private String pointsFaibles;
	private String evolution;
	private String recommandations;
	private String conclusion;
}
