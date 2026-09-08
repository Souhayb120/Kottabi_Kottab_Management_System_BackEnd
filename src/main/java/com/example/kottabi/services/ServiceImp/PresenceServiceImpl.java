package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceResponseDTO;
import com.example.kottabi.enums.Statut;
import com.example.kottabi.mapper.PresenceMapper;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Presence;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.repositories.PresenceRepo;
import com.example.kottabi.services.PresenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PresenceServiceImpl implements PresenceService {

	private final PresenceMapper presenceMapper;
	private final PresenceRepo presenceRepo;
	private final EleveRepo eleveRepo;

	public PresenceServiceImpl(PresenceMapper presenceMapper, PresenceRepo presenceRepo, EleveRepo eleveRepo) {
		this.presenceMapper = presenceMapper;
		this.presenceRepo = presenceRepo;
		this.eleveRepo = eleveRepo;
	}

	@Override
	public PresenceResponseDTO enregistrerAbsenceEleve(PresenceRequestDTO presenceRequestDTO) {
		Eleve eleve = eleveRepo.findById(presenceRequestDTO.getEleveId())
				.orElseThrow(() -> new RuntimeException("Eleve not found"));
		Presence presence = new Presence();

		presence.setDate(presenceRequestDTO.getDate());
		presence.setStatut(presenceRequestDTO.getStatut());
		presence.setEleve(eleve);

		Presence saved = presenceRepo.save(presence);

		PresenceResponseDTO response = presenceMapper.toDTO(saved);
		return response;
	}

	@Override
	public void modifierAbsenceStatut(long id, String statut) {
		Presence presence = presenceRepo.findById(id).orElseThrow(() -> new RuntimeException("Presence not found"));

		presence.setStatut(Statut.valueOf(statut));

		presenceRepo.save(presence);
	}

	@Override
	public void supprimerAbsence(long id) {
		Presence presence = presenceRepo.findById(id).orElseThrow(() -> new RuntimeException("Presence not found"));
		presenceRepo.delete(presence);
	}

	@Override
	public Page<Presence> consulterLesAbsences(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return presenceRepo.findAll(pageable);
	}
}
