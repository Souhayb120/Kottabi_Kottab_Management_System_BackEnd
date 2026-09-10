package com.example.kottabi.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.kottabi.DTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveResponseDTO;
import com.example.kottabi.config.PasswordGeneratorService;
import com.example.kottabi.mapper.EleveMapper;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.services.ServiceImp.EleveServiceImpl;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class EleveServiceTest {

	@Mock
	private EleveRepo eleveRepo;

	@Mock
	private PasswordGeneratorService passwordGenerator;

	@Mock
	private EleveMapper eleveMapper;

	@InjectMocks
	private EleveServiceImpl eleveService;

	@Test
	void ajouterEleve() {
		EleveRequestDTO eleve = new EleveRequestDTO(
			"souhayb22",
			"souhayb",
			"hadi",
			"samihadi@gmail.com",
			"1232132132",
			"12345677",
			LocalDate.now()
		);
		Eleve eleveEntity = new Eleve();
		EleveResponseDTO response = new EleveResponseDTO();
		when(eleveMapper.toEntity(eleve)).thenReturn(eleveEntity);
		when(passwordGenerator.generatePassword()).thenReturn("Generated123!");
		when(eleveRepo.save(eleveEntity)).thenReturn(eleveEntity);
		when(eleveMapper.toDTO(eleveEntity)).thenReturn(response);
		EleveResponseDTO createdEleve = eleveService.ajouterEleve(eleve);
		assertNotNull(createdEleve);
	}

	@Test
	void supprimerEleve() {}
}
