package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.EleveRequestDTO;
import com.example.kottabi.DTO.EleveResponseDTO;
import com.example.kottabi.Exceptions.ResourceNotFoundException;
import com.example.kottabi.config.PasswordGeneratorService;
import com.example.kottabi.enums.Role;
import com.example.kottabi.mapper.EleveMapper;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.services.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;

@Service
public class EleveServiceImpl implements EleveService {

	private final EleveRepo eleveRepo;
	private final EleveMapper eleveMapper;
	private final PasswordGeneratorService passwordGeneratorService;

	public EleveServiceImpl(EleveRepo eleveRepo, EleveMapper eleveMapper, PasswordGeneratorService passwordGeneratorService) {
		this.eleveRepo = eleveRepo;
		this.eleveMapper = eleveMapper;
        this.passwordGeneratorService = passwordGeneratorService;
    }

	@Override
	public EleveResponseDTO ajouterEleve(EleveRequestDTO dto) {
		Eleve eleve = eleveMapper.toEntity(dto);
		eleve.setPassword(passwordGeneratorService.generatePassword());
		Eleve saved = eleveRepo.save(eleve);
		EleveResponseDTO eleveResponseDTO = eleveMapper.toDTO(saved);
		return eleveResponseDTO;
	}

	@Override
	public Eleve editEleve(long id, EleveRequestDTO eleve) {
		Eleve eleve1 = eleveRepo.findById(id);
		eleve1.setUsername(eleve.getUsername());
		eleve1.setTel(eleve.getTel());
		eleve1.setDateNaissance(eleve.getDateNaissance());
		eleve1.setPrenom(eleve.getPrenom());
		eleve1.setNom(eleve.getNom());
		eleve1.setPassword(eleve.getPassword());
		return eleveRepo.save(eleve1);
	}

	@Override
	public void supprimerEleve(long id) {
		Eleve eleve = eleveRepo.findById(id);
		if(eleve != null){
			eleveRepo.delete(eleve);
		}else {
			System.out.println("Eleve Not Found !!");
		}
	}

	@Override
	public EleveResponseDTO consulterEleveById(long id) {
		Eleve eleve = eleveRepo.findById(id);

		EleveResponseDTO eleveResponseDTO = eleveMapper.toDTO(eleve);

		return eleveResponseDTO;
	}

	@Override
	public Page<Eleve> consulterEleves(int size, int page) {
		Pageable pageable = PageRequest.of(size,page);
		return eleveRepo.findAll(pageable);
	}
}
