package com.example.kottabi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Eleve extends UserEntity {

	private LocalDate DateLissance;


	@OneToMany(mappedBy = "eleve")
	private List<Presence> presenceList;

	@OneToMany(mappedBy = "eleve")
	private List<Progression> progressionList;

	@OneToMany(mappedBy = "eleve")
	private List<Participation> participationList;
}
