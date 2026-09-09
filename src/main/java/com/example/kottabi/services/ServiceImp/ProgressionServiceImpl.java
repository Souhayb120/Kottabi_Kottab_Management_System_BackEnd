package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.ProgressionRequestDTO;
import com.example.kottabi.DTO.ProgressionResponseDTO;
import com.example.kottabi.mapper.ProgressionMapper;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import com.example.kottabi.models.Progression;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.repositories.EnseignantRepo;
import com.example.kottabi.repositories.ProgressionRepo;
import com.example.kottabi.services.ProgressionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProgressionServiceImpl implements ProgressionService {

	private final ProgressionRepo progressionRepo;
	private final ProgressionMapper progressionMapper;
	private final EleveRepo eleveRepo;
	private final EnseignantRepo enseignantRepo;

	public ProgressionServiceImpl(
		ProgressionRepo progressionRepo,
		ProgressionMapper progressionMapper,
		EleveRepo eleveRepo,
		EnseignantRepo enseignantRepo
	) {
		this.progressionRepo = progressionRepo;
		this.progressionMapper = progressionMapper;
		this.eleveRepo = eleveRepo;
		this.enseignantRepo = enseignantRepo;
	}

	@CacheEvict(value = "progressions", allEntries = true)
	@Override
	public ProgressionResponseDTO ajouterProgression(ProgressionRequestDTO progressionRequestDTO) {
		Progression progression = progressionMapper.toEntity(progressionRequestDTO);

		Eleve eleve = eleveRepo
			.findById(progressionRequestDTO.getEleveId())
			.orElseThrow(() -> new RuntimeException("Eleve not found !!"));

		Enseignant enseignant = enseignantRepo
			.findById(progressionRequestDTO.getEnseignantId())
			.orElseThrow(() -> new RuntimeException("Enseignant not found !!"));

		progression.setEleve(eleve);
		progression.setEnseignant(enseignant);

		Progression progressionSaved = progressionRepo.save(progression);

		return progressionMapper.toDTO(progressionSaved);
	}

	@Cacheable(value = "progressions", key = "#page + '-' + #size")
	@Override
	public Page<ProgressionResponseDTO> consulterProgressions(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Progression> progressions = progressionRepo.findAll(pageable);

		return progressions.map(progression -> progressionMapper.toDTO(progression));
	}

	@CacheEvict(value = "progressions", allEntries = true)
	@Override
	public void supprimerProgression(long id) {
		Progression progression = progressionRepo
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Progression not found !!"));

		progressionRepo.delete(progression);
	}

	@CacheEvict(value = "progressions", allEntries = true)
	@Override
	public ProgressionResponseDTO modifierProgressionById(long id, ProgressionRequestDTO progressionRequestDTO) {
		Progression progression = progressionRepo
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Progression not found !!"));

		progression.setSourat(progressionRequestDTO.getSourat());
		progression.setVersetDebut(progressionRequestDTO.getVersetDebut());
		progression.setVersetFin(progressionRequestDTO.getVersetFin());

		Eleve eleve = eleveRepo
			.findById(progressionRequestDTO.getEleveId())
			.orElseThrow(() -> new RuntimeException("Eleve not found !!"));

		Enseignant enseignant = enseignantRepo
			.findById(progressionRequestDTO.getEnseignantId())
			.orElseThrow(() -> new RuntimeException("Enseignant not found !!"));

		progression.setEleve(eleve);
		progression.setEnseignant(enseignant);

		Progression progressionUpdated = progressionRepo.save(progression);

		return progressionMapper.toDTO(progressionUpdated);
	}

	@Cacheable(value = "progressionsByEleve", key = "#username + '-' + #page + '-' + #size")
	@Override
	public Page<ProgressionResponseDTO> consulterProgressionByEleveUserName(String username, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Progression> progressions = progressionRepo.findByEleveUsername(username, pageable);

		return progressions.map(progression -> progressionMapper.toDTO(progression));
	}
}
