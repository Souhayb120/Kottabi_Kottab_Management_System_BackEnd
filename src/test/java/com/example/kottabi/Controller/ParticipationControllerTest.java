package com.example.kottabi.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.kottabi.DTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationResponseDTO;
import com.example.kottabi.controller.ParticipationController;
import com.example.kottabi.services.ParticipationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParticipationControllerTest {

	@Mock
	private ParticipationService participationService;

	@InjectMocks
	private ParticipationController participationController;

	@Test
	void registerParticipation() {
		ParticipationResponseDTO participationResponseDTO = new ParticipationResponseDTO();
		ParticipationRequestDTO participationRequestDTO = new ParticipationRequestDTO(2.2, "bien", 2, 1, 2, 3);
		when(participationService.registerParticipation(participationRequestDTO)).thenReturn(participationResponseDTO);
		ParticipationResponseDTO result = participationController.registerParticipation(participationRequestDTO);
		assertNotNull(result);
		assertEquals(participationResponseDTO, result);
	}
}
