package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.ParticipationRequestDTO;
import com.example.kottabi.DTO.ParticipationResponseDTO;
import com.example.kottabi.mapper.ParticipationMapper;
import com.example.kottabi.models.Participation;
import com.example.kottabi.repositories.ParticipationRepo;
import com.example.kottabi.services.ParticipationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ParticipationServiceImpl implements ParticipationService {

	private final ParticipationRepo participationRepo;
	private final ParticipationMapper participationMapper;

	public ParticipationServiceImpl(ParticipationRepo participationRepo, ParticipationMapper participationMapper) {
		this.participationRepo = participationRepo;
		this.participationMapper = participationMapper;
	}

	@Override
	public ParticipationResponseDTO registerParticipation(ParticipationRequestDTO participationRequestDTO) {
		Participation participation = participationMapper.toEntity(participationRequestDTO);
		ParticipationResponseDTO participationResponseDTO = participationMapper.toDTO(participation);
		return participationResponseDTO;
	}

	@Override
	public Page<Participation> findAll(int page, int size) {
		Pageable pageableParticipations = PageRequest.of(page, size);
		return participationRepo.findAll(pageableParticipations);
	}

	@Override
	public void supprimerParticipation(long id) {
		Participation participation = participationRepo
			.findById(id)
			.orElseThrow(() -> new RuntimeException("participation not found !! "));
		participationRepo.delete(participation);
	}
}
