package com.example.kottabi.services.ServiceImp;
import com.example.kottabi.DTO.AI_DTO.*;
import com.example.kottabi.Exceptions.ResourceNotFoundException;
import com.example.kottabi.mapper.ParticipationMapper;
import com.example.kottabi.mapper.PresenceMapper;
import com.example.kottabi.mapper.ProgressionMapper;
import com.example.kottabi.models.Eleve;
import com.example.kottabi.models.Participation;
import com.example.kottabi.models.Presence;
import com.example.kottabi.models.Progression;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.services.AIRapportGenerator;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AiRapportGeneratorImpl implements AIRapportGenerator {

	private final EleveRepo eleveRepo;
	private final PresenceMapper presenceMapper;
	private final ParticipationMapper participationMapper;
	private final ProgressionMapper progressionMapper;

	public AiRapportGeneratorImpl(
		EleveRepo eleveRepo,
		PresenceMapper presenceMapper,
		ParticipationMapper participationMapper,
		ProgressionMapper progressionMapper
	) {
		this.eleveRepo = eleveRepo;
		this.presenceMapper = presenceMapper;
		this.participationMapper = participationMapper;
		this.progressionMapper = progressionMapper;
	}

	@Override
	public AiRapportRequestDTO createEleveRapportAi(long id) {
		Eleve eleve = eleveRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" user not found"));
		AiRapportRequestDTO rapport = new AiRapportRequestDTO();
		rapport.setNom(eleve.getNom());
		rapport.setPrenom(eleve.getPrenom());
		rapport.setDateNaissance(eleve.getDateNaissance());
		List<Presence> presences = eleve.getPresenceList();
		List<PresenceAI> presenceAIS = presences
			.stream()
			.map(presence -> presenceMapper.toPresenceAi(presence))
			.toList();
		rapport.setPresences(presenceAIS);
		List<Participation> participations = eleve.getParticipationList();
		List<ParticipationAI> participationAIS = participations
			.stream()
			.map(participation -> participationMapper.toParticipationAi(participation))
			.toList();
		rapport.setParticipations(participationAIS);

		List<Progression> progressions = eleve.getProgressionList();
		List<ProgressionAI> progressionAIS = progressions
			.stream()
			.map(progression -> progressionMapper.toProgressAi(progression))
			.toList();
		rapport.setProgressions(progressionAIS);
		return rapport;
	}
}
