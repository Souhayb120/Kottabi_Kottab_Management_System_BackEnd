package com.example.kottabi.models;


import com.example.kottabi.enums.NiveauHifz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Councour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String description;
    private LocalDate dateCreation;
    private NiveauHifz niveauHifz;



    @OneToMany(mappedBy = "councour")
    private List<Participation> participationList;



}
