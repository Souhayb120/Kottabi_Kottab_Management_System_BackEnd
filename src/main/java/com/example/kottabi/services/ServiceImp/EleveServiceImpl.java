package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.EleveDTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveDTO.EleveResponseDTO;
import com.example.kottabi.config.EmailService;
import com.example.kottabi.config.PasswordGeneratorService;
import com.example.kottabi.mapper.EleveMapper;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.services.EleveService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EleveServiceImpl implements EleveService {

	private final EleveRepo eleveRepo;
	private final EleveMapper eleveMapper;
	private final PasswordGeneratorService passwordGeneratorService;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;

	public EleveServiceImpl(
            EleveRepo eleveRepo,
            EleveMapper eleveMapper,
            PasswordGeneratorService passwordGeneratorService, EmailService emailService, PasswordEncoder passwordEncoder
    ) {
		this.eleveRepo = eleveRepo;
		this.eleveMapper = eleveMapper;
		this.passwordGeneratorService = passwordGeneratorService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

	@CacheEvict(value = "eleves", allEntries = true )
	@Override
	public EleveResponseDTO ajouterEleve(EleveRequestDTO dto) {
		Eleve eleve = eleveMapper.toEntity(dto);
		eleve.setPassword(passwordGeneratorService.generatePassword());
		emailService.sendPassword(eleve.getEmail(),eleve.getPassword());
		eleve.setPassword(passwordEncoder.encode(eleve.getPassword()));
		Eleve saved = eleveRepo.save(eleve);
		EleveResponseDTO eleveResponseDTO = eleveMapper.toDTO(saved);
		return eleveResponseDTO;
	}

	@CacheEvict(value = "eleves", allEntries = true )
	@Override
	public Eleve editEleve(long id, EleveRequestDTO eleve) {
		Eleve eleve1 = eleveRepo.findById(id).orElseThrow(() -> new RuntimeException("eleve not found !!"));
		eleve1.setUsername(eleve.getUsername());
		eleve1.setTel(eleve.getTel());
		eleve1.setDateNaissance(eleve.getDateNaissance());
		eleve1.setPrenom(eleve.getPrenom());
		eleve1.setNom(eleve.getNom());
		eleve1.setEmail(eleve.getEmail());
		eleve1.setPassword(eleve.getPassword());
		return eleveRepo.save(eleve1);
	}

	@CacheEvict(value = "eleves", allEntries = true)
	@Override
	public void supprimerEleve(long id) {
		Eleve eleve = eleveRepo.findById(id).orElseThrow(() -> new RuntimeException("eleve not found !!"));
		if (eleve != null) {
			eleveRepo.delete(eleve);
		} else {
			System.out.println("Eleve Not Found !!");
		}
	}

	@Cacheable(value = "eleves", key = "#id")
	@Override
	public EleveResponseDTO consulterEleveById(long id) {
		Eleve eleve = eleveRepo.findById(id).orElseThrow(() -> new RuntimeException("eleve not found !!"));
		EleveResponseDTO eleveResponseDTO = eleveMapper.toDTO(eleve);

		return eleveResponseDTO;
	}

	@Cacheable(value = "eleves", key = "#username")
	@Override
	public EleveResponseDTO consulterEleveByUsername(String username) {
		Eleve eleve = eleveRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("eleve not found !!"));
		return eleveMapper.toDTO(eleve);
	}

	@Cacheable(value = "eleves", key = "#page + '-' + #size")
	@Override
	public Page<EleveResponseDTO> consulterEleves(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Eleve> eleves = eleveRepo.findAll(pageable);
		return eleves.map(eleve -> eleveMapper.toDTO(eleve));
	}
}
