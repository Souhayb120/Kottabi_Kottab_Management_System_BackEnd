package com.example.kottabi.controller;

import com.example.kottabi.DTO.ProgressionDTO.ProgressionRequestDTO;
import com.example.kottabi.DTO.ProgressionDTO.ProgressionResponseDTO;
import com.example.kottabi.services.ProgressionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','ELEVE')")
	public Page<ProgressionResponseDTO> consulterProgressionsByEleve(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@PathVariable String username
	) {
		return progressionService.consulterProgressionByEleveUserName(username,page,size);
	}


	@GetMapping("/enseignant/{username}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public Page<ProgressionResponseDTO> consulterProgressionsByEnseignant(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@PathVariable String username
	) {
		return progressionService.consulterProgressionByEnseaignantUserName(username,page,size);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public ProgressionResponseDTO modifierProgression(
		@PathVariable long id,
		@Valid @RequestBody ProgressionRequestDTO progressionRequestDTO
	) {
		return progressionService.modifierProgressionById(id, progressionRequestDTO);
	}

	@GetMapping("/count")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public long countProgression(){
	return progressionService.countPrograssion();
	}

	@GetMapping("/recentProgressions")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public Page<ProgressionResponseDTO> recentProgressions(@RequestParam(defaultValue = "0") int page,
								   @RequestParam(defaultValue = "6") int size){

		return progressionService.getRecentProgressions(page,size);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void supprimerProgression(@PathVariable long id) {
		progressionService.supprimerProgression(id);
	}
}
