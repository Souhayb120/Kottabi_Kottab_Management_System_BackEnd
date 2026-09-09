package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationResponseDTO;
import com.example.kottabi.mapper.ParticipationMapper;
import com.example.kottabi.mapper.PresenceMapper;
import com.example.kottabi.models.Concour;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import com.example.kottabi.models.Participation;
import com.example.kottabi.repositories.ConcourRepo;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.repositories.EnseignantRepo;
import com.example.kottabi.repositories.ParticipationRepo;
import com.example.kottabi.services.ParticipationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParticipationServiceImpl implements ParticipationService {

	private final ParticipationRepo participationRepo;
	private final ParticipationMapper participationMapper;
	private final PresenceMapper presenceMapper;
	private final EleveRepo eleveRepo;
	private final EnseignantRepo enseignantRepo;
	private final ConcourRepo concourRepo;

	public ParticipationServiceImpl(
			ParticipationRepo participationRepo,
			ParticipationMapper participationMapper,
			PresenceMapper presenceMapper,
			EleveRepo eleveRepo,
			EnseignantRepo enseignantRepo,
			ConcourRepo concourRepo
	) {
		this.participationRepo = participationRepo;
		this.participationMapper = participationMapper;
		this.presenceMapper = presenceMapper;
		this.eleveRepo = eleveRepo;
		this.enseignantRepo = enseignantRepo;
		this.concourRepo = concourRepo;
	}

	@CacheEvict(value = "participations", allEntries = true)
	@Override
	public ParticipationResponseDTO registerParticipation(ParticipationRequestDTO participationRequestDTO) {
		Participation participation = participationMapper.toEntity(participationRequestDTO);
		Eleve eleve = eleveRepo
				.findById(participationRequestDTO.getEleveId())
				.orElseThrow(() -> new RuntimeException("eleve not found !! "));

		Enseignant enseignant = enseignantRepo
				.findById(participationRequestDTO.getEnseignantId())
				.orElseThrow(() -> new RuntimeException("enseignant not found !! "));

		Concour concour = concourRepo
				.findById(participationRequestDTO.getConcourId())
				.orElseThrow(() -> new RuntimeException("concour not found !! "));
		participation.setEleve(eleve);
		participation.setEnseignant(enseignant);
		participation.setConcour(concour);

		Participation participationSaved = participationRepo.save(participation);
		ParticipationResponseDTO participationResponseDTO = participationMapper.toDTO(participationSaved);
		return participationResponseDTO;
	}

	@Cacheable(value = "participations", key = "#page + '-' + #size")
	@Override
	public Page<ParticipationResponseDTO> findAll(int page, int size) {
		Pageable pageableParticipations = PageRequest.of(page, size);
		Page<Participation> participations = participationRepo.findAll(pageableParticipations);
		return participations.map(participation -> participationMapper.toDTO(participation));
	}

	@CacheEvict(value = "participations", allEntries = true)
	@Override
	public void supprimerParticipation(long id) {
		Participation participation = participationRepo
				.findById(id)
				.orElseThrow(() -> new RuntimeException("participation not found !! "));
		participationRepo.delete(participation);
	}
}