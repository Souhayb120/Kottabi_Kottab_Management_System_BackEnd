package com.example.kottabi.Config;

import static org.junit.jupiter.api.Assertions.*;

import com.example.kottabi.config.PasswordGeneratorService;
import org.junit.jupiter.api.Test;

class PasswordGeneratorServiceTest {

	private final PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();

	@Test
	void generatePasswordHasLengthTen() {
		String password = passwordGeneratorService.generatePassword();
		assertEquals(10, password.length());
	}
}