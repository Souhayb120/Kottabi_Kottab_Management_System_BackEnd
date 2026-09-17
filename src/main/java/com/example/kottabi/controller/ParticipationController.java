package com.example.kottabi.controller;

import com.example.kottabi.DTO.ParticipationDTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationDTO.ParticipationResponseDTO;
import com.example.kottabi.services.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participation")
public class ParticipationController {


	private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ParticipationResponseDTO registerParticipation(
		@Valid @RequestBody ParticipationRequestDTO participationRequestDTO
	) {
		return participationService.registerParticipation(participationRequestDTO);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Page<ParticipationResponseDTO> findAll(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return participationService.findAll(page, size);
	}

	@GetMapping("/eleve/{username}")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<ParticipationResponseDTO> findByEleveUsername(
		@PathVariable String username,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return participationService.findByEleveUsername(username, page, size);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void supprimerParticipation(@PathVariable long id) {
		participationService.supprimerParticipation(id);
	}
}
