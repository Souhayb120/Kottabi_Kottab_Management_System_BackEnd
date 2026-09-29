package com.example.kottabi.services.ServiceImp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.example.kottabi.DTO.ParticipationDTO.ParticipationRequestDTO;
import com.example.kottabi.mapper.ParticipationMapper;
import com.example.kottabi.models.Concour;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Enseignant;
import com.example.kottabi.models.Participation;
import com.example.kottabi.repositories.ConcourRepo;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.repositories.EnseignantRepo;
import com.example.kottabi.repositories.ParticipationRepo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParticipationServiceImplTest {

	@Mock
	private ParticipationRepo participationRepo;

	@Mock
	private ParticipationMapper participationMapper;

	@Mock
	private EleveRepo eleveRepo;

	@Mock
	private EnseignantRepo enseignantRepo;

	@Mock
	private ConcourRepo concourRepo;

	@InjectMocks
	private ParticipationServiceImpl participationService;

	@Test
	void registerParticipationTest() {
		ParticipationRequestDTO request = new ParticipationRequestDTO(15.5, "Tres bien", 1, 3L, 1L, 2L);

		Participation participation = new Participation();

		Eleve eleve = new Eleve();
		Enseignant enseignant = new Enseignant();
		Concour concour = new Concour();

		when(participationMapper.toEntity(request)).thenReturn(participation);

		when(eleveRepo.findById(1L)).thenReturn(Optional.of(eleve));
		when(enseignantRepo.findById(2L)).thenReturn(Optional.of(enseignant));
		when(concourRepo.findById(3L)).thenReturn(Optional.of(concour));

		when(participationRepo.save(participation)).thenReturn(participation);

		participationService.registerParticipation(request);

		verify(participationRepo).save(participation);
	}

	@Test
	void registerParticipationEleveNotFoundTest() {
		ParticipationRequestDTO request = new ParticipationRequestDTO(15.5, "Tres bien", 1, 3L, 1L, 2L);

		Participation participation = new Participation();

		when(participationMapper.toEntity(request)).thenReturn(participation);

		when(eleveRepo.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> participationService.registerParticipation(request));

		verify(participationRepo, never()).save(any());
	}
}
