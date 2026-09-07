package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.DTO.ChangePasswordRequest;
import com.example.kottabi.models.UserEntity;
import com.example.kottabi.repositories.UserRepository;
import com.example.kottabi.services.UserPasswordService;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserPasswordServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void changerLeMotDePasse(ChangePasswordRequest passwordRequest) {
		UserEntity user = userRepository
			.findByUsername(passwordRequest.getUserName())
			.orElseThrow(() -> new RuntimeException("user not found !!"));
		boolean isMatch = passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword());

		if (isMatch) {
			user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
			userRepository.save(user);
		} else {
			throw new RuntimeException("Ancien mot de passe incorrect");
		}
	}
}
