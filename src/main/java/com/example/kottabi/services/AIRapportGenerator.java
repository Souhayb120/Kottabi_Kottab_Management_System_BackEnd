package com.example.kottabi.services;

import com.example.kottabi.DTO.AI_DTO.AiRapportRequestDTO;
import com.example.kottabi.DTO.AI_DTO.AiRapportResponceDTO;
import org.springframework.stereotype.Service;

public interface AIRapportGenerator {
    AiRapportRequestDTO createEleveRapportAi(long id);
}
