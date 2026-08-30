package com.example.kottabi.config;

import com.example.kottabi.DTO.AuthResponse;
import com.example.kottabi.DTO.UserAuthRequest;
import com.example.kottabi.DTO.UserLoginDTO;
import com.example.kottabi.models.Admin;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import com.example.kottabi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void register(UserAuthRequest request) {
		Eleve eleve = new Eleve();
		Enseignant enseignant = new Enseignant();
		Admin admin = new Admin();
		switch (request.getRole().name()) {
			case "ELEVE" -> {
				eleve.setUserName(request.getUserName());
				eleve.setRole(request.getRole());
				eleve.setPassword(passwordEncoder.encode(request.getPassword()));
				eleve.setPrenom(request.getPrenom());
				eleve.setNom(request.getNom());
				eleve.setDateLissance(request.getDateLissance());
				eleve.setTel(request.getTel());
				userRepository.save(eleve);
			}
			case "ADMIN" -> {
				admin.setUserName(request.getUserName());
				admin.setTel(request.getTel());
				admin.setPassword(passwordEncoder.encode(request.getPassword()));
				admin.setRole(request.getRole());
				userRepository.save(admin);
			}
			case "ENSEIGNANT" -> {
				enseignant.setUserName(request.getUserName());
				enseignant.setNom(request.getNom());
				eleve.setPrenom(request.getPrenom());
				enseignant.setTel(request.getTel());
				enseignant.setPassword(passwordEncoder.encode(request.getPassword()));
				enseignant.setRole(request.getRole());
				enseignant.setSpecialite(request.getSpecialite());
				enseignant.setDescription(request.getDescription());
				userRepository.save(enseignant);
			}
		}
	}

	public AuthResponse login(UserLoginDTO request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
		);
		UserDetails user = userDetailsService.loadUserByUsername(request.getUserName());
		String token = jwtUtil.generateToken(user);
		return new AuthResponse(token);
	}
}
