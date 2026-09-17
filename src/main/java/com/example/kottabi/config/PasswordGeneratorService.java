package com.example.kottabi.config;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordGeneratorService {

	public String generatePassword() {
		PasswordGenerator passwordGenerator = new PasswordGenerator();

		String code = passwordGenerator.generatePassword(
			10,
			new CharacterRule(EnglishCharacterData.LowerCase, 2),
			new CharacterRule(EnglishCharacterData.UpperCase, 2),
			new CharacterRule(EnglishCharacterData.Digit, 2)
		);
		return code;
	}
}
