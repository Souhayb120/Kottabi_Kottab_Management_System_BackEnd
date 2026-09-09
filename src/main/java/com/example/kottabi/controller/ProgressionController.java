package com.example.kottabi.controller;

import com.example.kottabi.DTO.ProgressionRequestDTO;
import com.example.kottabi.DTO.ProgressionResponseDTO;
import com.example.kottabi.services.ProgressionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progressions")
public class ProgressionController {

	private final ProgressionService progressionService;

    public ProgressionController(ProgressionService progressionService) {
        this.progressionService = progressionService;
    }

    @PostMapping
	public ProgressionResponseDTO ajouterProgression(@Valid @RequestBody ProgressionRequestDTO progressionRequestDTO) {
		return progressionService.ajouterProgression(progressionRequestDTO);
	}

	@GetMapping
	public Page<ProgressionResponseDTO> consulterProgressions(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return progressionService.consulterProgressions(page, size);
	}

	@GetMapping("/eleve/{username}")
	public Page<ProgressionResponseDTO> consulterProgressionsByEleve(
		@PathVariable String username,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return progressionService.consulterProgressionByEleveUserName(username, page, size);
	}

	@PutMapping("/{id}")
	public ProgressionResponseDTO modifierProgression(
		@PathVariable long id,
		@Valid @RequestBody ProgressionRequestDTO progressionRequestDTO
	) {
		return progressionService.modifierProgressionById(id, progressionRequestDTO);
	}

	@DeleteMapping("/{id}")
	public void supprimerProgression(@PathVariable long id) {
		progressionService.supprimerProgression(id);
	}
}
