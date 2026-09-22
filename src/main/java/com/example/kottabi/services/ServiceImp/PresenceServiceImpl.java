package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.PresenceDTO.PresenceRequestDTO;
import com.example.kottabi.DTO.PresenceDTO.PresenceResponseDTO;
import com.example.kottabi.enums.Statut;
import com.example.kottabi.mapper.PresenceMapper;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Presence;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.repositories.PresenceRepo;
import com.example.kottabi.services.PresenceService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

	@CacheEvict(value = "presences", allEntries = true)
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

	@CacheEvict(value = "presences", allEntries = true)
	@Override
	public void modifierAbsenceStatut(long id, String statut) {
		Presence presence = presenceRepo.findById(id).orElseThrow(() -> new RuntimeException("Presence not found"));

		presence.setStatut(Statut.valueOf(statut));

		presenceRepo.save(presence);
	}

	@CacheEvict(value = "presences", allEntries = true)
	@Override
	public void supprimerAbsence(long id) {
		Presence presence = presenceRepo.findById(id).orElseThrow(() -> new RuntimeException("Presence not found"));
		presenceRepo.delete(presence);
	}

	@Cacheable(value = "presences", key = "#page + '-' + #size")
	@Override
	public Page<PresenceResponseDTO> consulterLesAbsences(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Presence> presences = presenceRepo.findAll(pageable);
		return presences.map(presence -> presenceMapper.toDTO(presence));
	}

	@Cacheable(value = "presences", key = "#username + '-' + #page + '-' + #size")
	@Override
	public Page<PresenceResponseDTO> consulterPresencesByEleve(String username, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Presence> presences = presenceRepo.findByEleveUsername(username, pageable);
		return presences.map(presence -> presenceMapper.toDTO(presence));
	}

	@Override
	public Page<PresenceResponseDTO> getCurrentDayPresence(int page , int size) {
		LocalDate dateNow = LocalDate.now();
		Pageable pageable = PageRequest.of(page, size);
		Page<Presence> presences = presenceRepo.findByDate(dateNow ,pageable);
		Page<PresenceResponseDTO> presenceResponseDTOS = presences.map(presence -> presenceMapper.toDTO(presence));
		return presenceResponseDTOS;
	}

	@Override
	public long countByStatut(String statut) {
		return presenceRepo.countByStatut(Statut.valueOf(statut));
	}



	@Override
	public Page<PresenceResponseDTO> getPresenceByStatut(String statut , int page , int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Presence> presences = presenceRepo.findByStatut(Statut.valueOf(statut) ,pageable);
		Page<PresenceResponseDTO> presenceResponseDTOS = presences.map(presence -> presenceMapper.toDTO(presence));
		return presenceResponseDTOS;
	}
}