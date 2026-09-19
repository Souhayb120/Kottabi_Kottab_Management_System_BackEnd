package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.ConcourDTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
import com.example.kottabi.mapper.ConcourMapper;
import com.example.kottabi.models.Concour;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.repositories.ConcourRepo;
import com.example.kottabi.services.ConcourService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConcourServiceImpl implements ConcourService {

	private final ConcourMapper concourMapper;
	private final ConcourRepo concourRepo;

	public ConcourServiceImpl(ConcourMapper concourMapper, ConcourRepo concourRepo) {
		this.concourMapper = concourMapper;
		this.concourRepo = concourRepo;
	}

	@CacheEvict(value = "concours", allEntries = true)
	@Override
	public ConcourResponseDTO ajouterConcour(ConcourRequestDTO concour) {
		Concour concourEntity = concourMapper.toEntity(concour);
		Concour concourSave = concourRepo.save(concourEntity);
		ConcourResponseDTO concourResponseDTO = concourMapper.toDTO(concourSave);
		return concourResponseDTO;
	}

	@CacheEvict(value = "concours", allEntries = true)
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

	@CacheEvict(value = "concours", allEntries = true)
	@Override
	public void supprimerConcour(long id) {
		Concour concour = concourRepo.findById(id).orElseThrow(() -> new RuntimeException("Concour not found"));

		concourRepo.delete(concour);
	}

	@Cacheable(value = "concours", key = "#id")
	@Override
	public ConcourResponseDTO consulterConcourById(long id) {
		Concour concour = concourRepo.findById(id).orElseThrow(() -> new RuntimeException("Concour not found"));
		ConcourResponseDTO concourResponseDTO = concourMapper.toDTO(concour);
		return concourResponseDTO;
	}

	@Cacheable(value = "concours", key = "#pageNumber + '-' + #pageSize")
	@Override
	public Page<ConcourResponseDTO> consulterConcours(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Concour> concours = concourRepo.findAll(pageable);
		return  concours.map(concour -> concourMapper.toDTO(concour));
	}

	@Override
	public long countConcours() {
		return concourRepo.count();
	}


}