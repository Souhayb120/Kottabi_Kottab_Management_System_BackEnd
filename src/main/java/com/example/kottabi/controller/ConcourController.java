package com.example.kottabi.controller;

import com.example.kottabi.DTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourResponseDTO;
import com.example.kottabi.services.ConcourService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concour")
public class ConcourController {

	private final ConcourService concourService;

	public ConcourController(ConcourService concourService) {
		this.concourService = concourService;
	}

	@PostMapping
	public ConcourResponseDTO ajouterConcour(@Valid @RequestBody ConcourRequestDTO concour) {
		return concourService.ajouterConcour(concour);
	}

	@GetMapping("{id}")
	public ConcourResponseDTO findConcourById(@PathVariable long id) {
		return concourService.consulterConcourById(id);
	}

	@DeleteMapping("{id}")
	public void supprimerConcour(@PathVariable long id) {
		concourService.supprimerConcour(id);
	}

	@PutMapping("/{id}")
	public ConcourResponseDTO modifierConcour(@PathVariable long id, @RequestBody ConcourRequestDTO concourRequestDTO) {
		return concourService.editConcour(id, concourRequestDTO);
	}
}
