package com.example.kottabi.controller;

import com.example.kottabi.DTO.PresenceDTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceDTO.PresenceResponseDTO;
import com.example.kottabi.services.PresenceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/presence")
public class PresenceController {

	private final PresenceService presenceService;

	public PresenceController(PresenceService presenceService) {
		this.presenceService = presenceService;
	}

	private void verifierAccesEleve(UserDetails currentUser, String username) {
		boolean isEleve = currentUser.getAuthorities()
			.stream()
			.anyMatch(a -> a.getAuthority().equals("ROLE_ELEVE"));
		if (isEleve && !currentUser.getUsername().equals(username)) {
			throw new AccessDeniedException("Vous ne pouvez consulter que vos propres données.");
		}
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public PresenceResponseDTO enregistrerAbsenceEleve(@Valid @RequestBody PresenceRequestDTO presenceRequestDTO) {
		return presenceService.enregistrerAbsenceEleve(presenceRequestDTO);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public void modifierAbsenceStatut(@PathVariable long id, @RequestParam String statut) {
		presenceService.modifierAbsenceStatut(id, statut);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public void supprimerAbsence(@PathVariable long id) {
		presenceService.supprimerAbsence(id);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public Page<PresenceResponseDTO> consulterLesAbsences(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return presenceService.consulterLesAbsences(page, size);
	}

	@GetMapping("/eleve/{username}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','ELEVE')")
	public Page<PresenceResponseDTO> consulterPresencesByEleve(
		@PathVariable String username,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@AuthenticationPrincipal UserDetails currentUser
	) {
		verifierAccesEleve(currentUser, username);
		return presenceService.consulterPresencesByEleve(username, page, size);
	}

	@GetMapping("countPresence/{statut}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public long countPresence(@PathVariable String statut) {
		return presenceService.countByStatut(statut);
	}




    @GetMapping("/Recent6Presence")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public Page<PresenceResponseDTO> cosulterRecentPresence(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return presenceService.getCurrentDayPresence(page ,size);
    }


    @GetMapping("/filterPresence")
    @PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
    public Page<PresenceResponseDTO> filterPresenceByStatut(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String statut
    ) {
        return presenceService.getPresenceByStatut(statut , page , size);
    }
}
