package com.example.kottabi.controller;

import com.example.kottabi.DTO.ProgressionDTO.ProgressionRequestDTO;
import com.example.kottabi.DTO.ProgressionDTO.ProgressionResponseDTO;
import com.example.kottabi.services.ProgressionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progressions")
public class ProgressionController {

	private final ProgressionService progressionService;

    public ProgressionController(ProgressionService progressionService) {
        this.progressionService = progressionService;
    }

    @PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public ProgressionResponseDTO ajouterProgression(@Valid @RequestBody ProgressionRequestDTO progressionRequestDTO) {
		return progressionService.ajouterProgression(progressionRequestDTO);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public Page<ProgressionResponseDTO> consulterProgressions(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return progressionService.consulterProgressions(page, size);
	}

	@GetMapping("/eleve/{username}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public ProgressionResponseDTO consulterProgressionsByEleve(
		@PathVariable String username
	) {
		return progressionService.consulterProgressionByEleveUserName(username);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public ProgressionResponseDTO modifierProgression(
		@PathVariable long id,
		@Valid @RequestBody ProgressionRequestDTO progressionRequestDTO
	) {
		return progressionService.modifierProgressionById(id, progressionRequestDTO);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void supprimerProgression(@PathVariable long id) {
		progressionService.supprimerProgression(id);
	}
}
