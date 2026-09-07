package com.example.kottabi.models;

import com.example.kottabi.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Eleve extends UserEntity {



	private LocalDate dateNaissance;

	public Eleve() {
		super();
		setRole(Role.ELEVE);

	}

	@OneToMany(mappedBy = "eleve")
	private List<Presence> presenceList;

	@OneToMany(mappedBy = "eleve")
	private List<Progression> progressionList;

	@OneToMany(mappedBy = "eleve")
	private List<Participation> participationList;
}
