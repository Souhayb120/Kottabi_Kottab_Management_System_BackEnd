package com.example.kottabi.controller;

import com.example.kottabi.DTO.EnseignantDTO.EnseignantRequestDTO;
import com.example.kottabi.DTO.EnseignantDTO.EnseignantResponseDTO;
import com.example.kottabi.models.Enseignant;
import com.example.kottabi.services.EnseignantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enseignant")
public class EnseignantController {

	private final EnseignantService enseignantService;

	public EnseignantController(EnseignantService enseignantService) {
		this.enseignantService = enseignantService;
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Page<EnseignantResponseDTO> consulterEnseignants(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return enseignantService.consulterEnseignants(page, size);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public EnseignantResponseDTO ajouterEnseignant(@Valid @RequestBody EnseignantRequestDTO enseignantRequestDTO) {
		return enseignantService.ajouterEnseignant(enseignantRequestDTO);
	}

	@GetMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public EnseignantResponseDTO findEnseignantById(@PathVariable long id) {
		return enseignantService.consulterEnseignantById(id);
	}

	@GetMapping("/specialite/{specialite}")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<EnseignantResponseDTO> findEnseignantsBySpecialite(
		@PathVariable String specialite,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return enseignantService.consulterEnseignantBySpecialite(specialite, page, size);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void SupprimerEnseignant(@PathVariable long id) {
		enseignantService.supprimerEnseignant(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Enseignant modifierEnseignant(
		@PathVariable long id,
		@RequestBody EnseignantRequestDTO enseignantRequestDTO
	) {
		return enseignantService.editEnseignant(id, enseignantRequestDTO);
	}
}
