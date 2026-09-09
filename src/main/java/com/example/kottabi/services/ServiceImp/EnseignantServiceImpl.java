package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.EnseignantRequestDTO;
import com.example.kottabi.DTO.EnseignantResponseDTO;
import com.example.kottabi.Exceptions.ResourceNotFoundException;
import com.example.kottabi.config.PasswordGeneratorService;
import com.example.kottabi.mapper.EnseignantMapper;
import com.example.kottabi.models.Enseignant;
import com.example.kottabi.repositories.EnseignantRepo;
import com.example.kottabi.services.EnseignantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnseignantServiceImpl implements EnseignantService {

	private final EnseignantRepo enseignantRepo;
	private final EnseignantMapper enseignantMapper;
	private final PasswordGeneratorService passwordGeneratorService;

	public EnseignantServiceImpl(
		EnseignantRepo enseignantRepo,
		EnseignantMapper enseignantMapper,
		PasswordGeneratorService passwordGeneratorService
	) {
		this.enseignantRepo = enseignantRepo;
		this.enseignantMapper = enseignantMapper;
		this.passwordGeneratorService = passwordGeneratorService;
	}

	@Override
	public EnseignantResponseDTO ajouterEnseignant(EnseignantRequestDTO dto) {
		Enseignant enseignant = enseignantMapper.toEntity(dto);
		enseignant.setPassword(passwordGeneratorService.generatePassword());
		Enseignant saved = enseignantRepo.save(enseignant);
		EnseignantResponseDTO enseignantResponseDTO = enseignantMapper.toDTO(saved);
		return enseignantResponseDTO;
	}

	@Override
	public Enseignant editEnseignant(long id, EnseignantRequestDTO enseignantRequestDTO) {
		Enseignant enseignant = enseignantRepo
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Enseignant not found !!"));

		enseignant.setUsername(enseignantRequestDTO.getUsername());

		enseignant.setPrenom(enseignantRequestDTO.getPrenom());

		enseignant.setNom(enseignantRequestDTO.getNom());

		enseignant.setTel(enseignantRequestDTO.getTel());
		enseignant.setEmail(enseignantRequestDTO.getEmail());

		enseignant.setSpecialite(enseignantRequestDTO.getSpecialite());

		enseignant.setDescription(enseignantRequestDTO.getDescription());
		return enseignantRepo.save(enseignant);
	}

	@Override
	public void supprimerEnseignant(long id) {
		Enseignant enseignant = enseignantRepo
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Enseignant not found !!"));

		enseignantRepo.delete(enseignant);
	}

	@Override
	public EnseignantResponseDTO consulterEnseignantById(long id) {
		Enseignant enseignant = enseignantRepo
			.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Enseignant not found !!"));

		return enseignantMapper.toDTO(enseignant);
	}

	@Override
	public Page<EnseignantResponseDTO> consulterEnseignantBySpecialite(String specialite, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Enseignant> enseignants = enseignantRepo.findBySpecialite(specialite, pageable);

		return enseignants.map(enseignantMapper::toDTO);
	}

	@Override
	public Page<EnseignantResponseDTO> consulterEnseignants(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Enseignant> enseignants = enseignantRepo.findAll(pageable);

		return enseignants.map(enseignant -> enseignantMapper.toDTO(enseignant));
	}
}
