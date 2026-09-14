package com.example.kottabi.DTO.AI_DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EleveRapport {
    private String summary;
    private String strengths;
    private String weaknesses;
    private String recommendations;
}
