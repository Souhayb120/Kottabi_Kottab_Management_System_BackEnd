package com.example.kottabi.services.ServiceImp;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import com.example.kottabi.DTO.ConcourDTO.ConcourRequestDTO;
import com.example.kottabi.DTO.ConcourDTO.ConcourResponseDTO;
import com.example.kottabi.mapper.ConcourMapper;
import com.example.kottabi.models.Concour;
import com.example.kottabi.repositories.ConcourRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConcourServiceImplTest {

	@Mock
	private ConcourMapper concourMapper;

	@Mock
	private ConcourRepo concourRepo;

	@InjectMocks
	private ConcourServiceImpl concourService;

	@Test
	void ajouterConcourTest() {
		ConcourRequestDTO request = new ConcourRequestDTO();

		Concour entity = new Concour();

		Concour saved = new Concour();

		ConcourResponseDTO response = new ConcourResponseDTO();

		when(concourMapper.toEntity(request)).thenReturn(entity);

		when(concourRepo.save(entity)).thenReturn(saved);

		when(concourMapper.toDTO(saved)).thenReturn(response);

		ConcourResponseDTO result = concourService.ajouterConcour(request);

		assertSame(response, result);

		verify(concourRepo).save(entity);
	}
}
