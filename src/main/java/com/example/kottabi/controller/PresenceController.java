package com.example.kottabi.controller;

import com.example.kottabi.DTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceResponseDTO;
import com.example.kottabi.models.Presence;
import com.example.kottabi.services.PresenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/presence")
public class PresenceController {


    private final PresenceService presenceService;

    public PresenceController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @PostMapping
    public PresenceResponseDTO enregistrerAbsenceEleve(
            @Valid @RequestBody PresenceRequestDTO presenceRequestDTO) {

        return presenceService.enregistrerAbsenceEleve(presenceRequestDTO);
    }

    @PutMapping("/{id}")
    public void modifierAbsenceStatut(
            @PathVariable long id,
            @RequestParam String statut) {

        presenceService.modifierAbsenceStatut(id, statut);
    }

    @DeleteMapping("/{id}")
    public void supprimerAbsence(@PathVariable long id) {

        presenceService.supprimerAbsence(id);
    }

    @GetMapping
    public Page<Presence> consulterLesAbsences(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return presenceService.consulterLesAbsences(page, size);
    }
}
