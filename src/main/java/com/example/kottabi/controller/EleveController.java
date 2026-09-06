package com.example.kottabi.controller;

import com.example.kottabi.DTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveResponseDTO;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.services.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eleve")
public class EleveController {

	@Autowired
	private EleveService eleveService;


	@GetMapping()
	public Page<Eleve> consulterEleves(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		return eleveService.consulterEleves(page, size);
	}

	@PostMapping
	public EleveResponseDTO ajouterEleve(@RequestBody EleveRequestDTO eleve) {
		return eleveService.ajouterEleve(eleve);
	}

	@GetMapping("{id}")
	public EleveResponseDTO findEleveById(@PathVariable long id){
		return eleveService.consulterEleveById(id);
	}

	@DeleteMapping("{id}")
	public void SupprimerEleve(@PathVariable long id){
		eleveService.supprimerEleve(id);
	}

	@PutMapping("/{id}")
	public Eleve modifierEleve(@PathVariable long id , @RequestBody EleveRequestDTO eleveRequestDTO){
	return	eleveService.editEleve(id,eleveRequestDTO);
	}
}
