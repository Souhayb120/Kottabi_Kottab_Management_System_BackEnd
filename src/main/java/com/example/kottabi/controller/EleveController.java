package com.example.kottabi.controller;

import com.example.kottabi.DTO.EleveDTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveDTO.EleveResponseDTO;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.services.EleveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eleve")
public class EleveController {

	@Autowired
	private EleveService eleveService;


	@GetMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public Page<EleveResponseDTO> consulterEleves(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		return eleveService.consulterEleves(page, size);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public EleveResponseDTO ajouterEleve(@Valid @RequestBody EleveRequestDTO eleve) {
		return eleveService.ajouterEleve(eleve);
	}

	@GetMapping("username/{username}")
	@PreAuthorize("hasAnyRole('ADMIN','ELEVE')")
	public EleveResponseDTO findEleveByUsername(@PathVariable String username){
		return eleveService.consulterEleveByUsername(username);
	}

	@GetMapping("/countEleves")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public long findEleveByUsername(){
		return eleveService.countEleve();
	}

	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMIN','ELEVE')")
	public EleveResponseDTO findEleveById(@PathVariable long id){
		return eleveService.consulterEleveById(id);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void SupprimerEleve(@PathVariable long id){
		eleveService.supprimerEleve(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Eleve modifierEleve(@PathVariable long id , @RequestBody EleveRequestDTO eleveRequestDTO){
	return	eleveService.editEleve(id,eleveRequestDTO);
	}
}
