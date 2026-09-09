package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationResponseDTO;
import com.example.kottabi.mapper.ParticipationMapper;
import com.example.kottabi.mapper.PresenceMapper;
import com.example.kottabi.models.Participation;
import com.example.kottabi.repositories.ParticipationRepo;
import com.example.kottabi.services.ParticipationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParticipationServiceImpl implements ParticipationService {

	private final ParticipationRepo participationRepo;
	private final ParticipationMapper participationMapper;
	private final PresenceMapper presenceMapper;

	public ParticipationServiceImpl(ParticipationRepo participationRepo, ParticipationMapper participationMapper, PresenceMapper presenceMapper) {
		this.participationRepo = participationRepo;
		this.participationMapper = participationMapper;
		this.presenceMapper = presenceMapper;
	}

	@Override
	public ParticipationResponseDTO registerParticipation(ParticipationRequestDTO participationRequestDTO) {
		Participation participation = participationMapper.toEntity(participationRequestDTO);
		ParticipationResponseDTO participationResponseDTO = participationMapper.toDTO(participation);
		return participationResponseDTO;
	}

	@Override
	public Page<ParticipationResponseDTO> findAll(int page, int size) {
		Pageable pageableParticipations = PageRequest.of(page, size);
		Page<Participation> participations = participationRepo.findAll(pageableParticipations);
		return participations.map(participation -> participationMapper.toDTO(participation));
	}

	@Override
	public void supprimerParticipation(long id) {
		Participation participation = participationRepo
			.findById(id)
			.orElseThrow(() -> new RuntimeException("participation not found !! "));
		participationRepo.delete(participation);
	}
}
