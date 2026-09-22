package com.example.kottabi.controller;

import com.example.kottabi.DTO.ConcourDTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
import com.example.kottabi.DTO.ParticipationDTO.ParticipationResponseDTO;
import com.example.kottabi.services.ConcourService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concour")
public class ConcourController {

	private final ConcourService concourService;

	public ConcourController(ConcourService concourService) {
		this.concourService = concourService;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ConcourResponseDTO ajouterConcour(@Valid @RequestBody ConcourRequestDTO concour) {
		return concourService.ajouterConcour(concour);
	}

	@GetMapping("/countConcours")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public long countConcours(){
		return concourService.countConcours();
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public Page<ConcourResponseDTO> findAllConcours(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size
	) {
		return concourService.consulterConcours(page, size);
	}


	@GetMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ConcourResponseDTO findConcourById(@PathVariable long id) {
		return concourService.consulterConcourById(id);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void supprimerConcour(@PathVariable long id) {
		concourService.supprimerConcour(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ConcourResponseDTO modifierConcour(@PathVariable long id, @RequestBody ConcourRequestDTO concourRequestDTO) {
		return concourService.editConcour(id, concourRequestDTO);
	}
}
