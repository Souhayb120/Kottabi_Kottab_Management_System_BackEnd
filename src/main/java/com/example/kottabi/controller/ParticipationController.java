package com.example.kottabi.controller;

import com.example.kottabi.DTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationResponseDTO;
import com.example.kottabi.models.Participation;
import com.example.kottabi.services.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participation")
public class ParticipationController {


	private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping
	public ParticipationResponseDTO registerParticipation(
		@Valid @RequestBody ParticipationRequestDTO participationRequestDTO
	) {
		return participationService.registerParticipation(participationRequestDTO);
	}

	@GetMapping
	public Page<Participation> findAll(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return participationService.findAll(page, size);
	}

	@DeleteMapping("/{id}")
	public void supprimerParticipation(@PathVariable long id) {
		participationService.supprimerParticipation(id);
	}
}
