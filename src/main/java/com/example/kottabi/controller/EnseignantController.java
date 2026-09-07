package com.example.kottabi.controller;

import com.example.kottabi.DTO.EnseignantRequestDTO;
import com.example.kottabi.DTO.EnseignantResponseDTO;
import com.example.kottabi.models.Enseignant;
import com.example.kottabi.services.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enseignant")
public class EnseignantController {


    private final EnseignantService enseignantService;

    public EnseignantController(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    @GetMapping()
    public Page<Enseignant> consulterEnseignants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return enseignantService.consulterEnseignants(page, size);
    }

    @PostMapping
    public EnseignantResponseDTO ajouterEnseignant(
            @RequestBody EnseignantRequestDTO enseignantRequestDTO
    ) {
        return enseignantService.ajouterEnseignant(enseignantRequestDTO);
    }

    @GetMapping("{id}")
    public EnseignantResponseDTO findEnseignantById(
            @PathVariable long id
    ) {
        return enseignantService.consulterEnseignantById(id);
    }

    @GetMapping("/specialite/{specialite}")
    public Page<EnseignantResponseDTO> findEnseignantsBySpecialite(
            @PathVariable String specialite,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return enseignantService.consulterEnseignantBySpecialite(
                specialite,
                page,
                size
        );
    }

    @DeleteMapping("{id}")
    public void SupprimerEnseignant(
            @PathVariable long id
    ) {
        enseignantService.supprimerEnseignant(id);
    }

    @PutMapping("/{id}")
    public Enseignant modifierEnseignant(
            @PathVariable long id,
            @RequestBody EnseignantRequestDTO enseignantRequestDTO
    ) {
        return enseignantService.editEnseignant(
                id,
                enseignantRequestDTO
        );
    }
}