package com.example.kottabi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enseignant extends UserEntity{
    private String specialite;
    private String description;

    @OneToMany(mappedBy = "enseignant")
    private List<Progression> progressionList;
    @OneToMany(mappedBy = "enseignant")
    private List<Participation> participationList;
}
