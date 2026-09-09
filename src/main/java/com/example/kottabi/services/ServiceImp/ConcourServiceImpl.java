package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourResponseDTO;
import com.example.kottabi.mapper.ConcourMapper;
import com.example.kottabi.models.Concour;
import com.example.kottabi.repositories.ConcourRepo;
import com.example.kottabi.services.ConcourService;
import org.springframework.stereotype.Service;

@Service
public class ConcourServiceImpl implements ConcourService {

	private final ConcourMapper concourMapper;
	private final ConcourRepo concourRepo;

	public ConcourServiceImpl(ConcourMapper concourMapper, ConcourRepo concourRepo) {
		this.concourMapper = concourMapper;
		this.concourRepo = concourRepo;
	}

	@Override
	public ConcourResponseDTO ajouterConcour(ConcourRequestDTO concour) {
		Concour concourEntity = concourMapper.toEntity(concour);
		Concour concourSave = concourRepo.save(concourEntity);
		ConcourResponseDTO concourResponseDTO = concourMapper.toDTO(concourSave);
		return concourResponseDTO;
	}

	@Override
	public ConcourResponseDTO editConcour(long id, ConcourRequestDTO dto) {
		Concour concour = concourRepo.findById(id).orElseThrow(() -> new RuntimeException("Concour not found"));

		concour.setNom(dto.getNom());
		concour.setDescription(dto.getDescription());
		concour.setDateCreation(dto.getDateCreation());
		concour.setNiveauHifz(dto.getNiveauHifz());

		Concour saved = concourRepo.save(concour);

		ConcourResponseDTO concourResponseDTO = concourMapper.toDTO(saved);
		return concourResponseDTO;
	}

	@Override
	public void supprimerConcour(long id) {
		Concour concour = concourRepo.findById(id).orElseThrow(() -> new RuntimeException("Concour not found"));

		concourRepo.delete(concour);
	}

	@Override
	public ConcourResponseDTO consulterConcourById(long id) {
		Concour concour = concourRepo.findById(id).orElseThrow(() -> new RuntimeException("Concour not found"));
		ConcourResponseDTO concourResponseDTO = concourMapper.toDTO(concour);
		return concourResponseDTO;
	}
}
