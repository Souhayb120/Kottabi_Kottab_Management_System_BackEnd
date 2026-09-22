package com.example.kottabi.controller;

import com.example.kottabi.DTO.ParticipationDTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationDTO.ParticipationResponseDTO;
import com.example.kottabi.services.ParticipationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participation")
public class ParticipationController {


	private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
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
	@PreAuthorize("hasRole('ADMIN')")
	public ParticipationResponseDTO registerParticipation(
		@Valid @RequestBody ParticipationRequestDTO participationRequestDTO
	) {
		return participationService.registerParticipation(participationRequestDTO);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public Page<ParticipationResponseDTO> findAll(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return participationService.findAll(page, size);
	}

	@GetMapping("/enseignant/{username}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public Page<ParticipationResponseDTO> findByEnseignantUsername(
		@PathVariable String username,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return participationService.findParticipationByEnseignantUserName(username,page,size);
	}

	@GetMapping("/eneignant/{username}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT','ELEVE')")
	public Page<ParticipationResponseDTO> findByEnseignantUsername(
			@PathVariable String username,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UserDetails currentUser
	) {
		verifierAccesEleve(currentUser, username);
		return participationService.findByEleveUsername(username, page, size);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void supprimerParticipation(@PathVariable long id) {
		participationService.supprimerParticipation(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','ENSEIGNANT')")
	public ParticipationResponseDTO modifierParticipation(
		@PathVariable long id,
		@Valid @RequestBody ParticipationRequestDTO participationRequestDTO
	) {
		return participationService.modifierParticipation(id, participationRequestDTO);
	}
}
