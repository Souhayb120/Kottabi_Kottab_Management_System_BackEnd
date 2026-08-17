package com.example.kottabi.controller;

import com.example.kottabi.models.Eleve;
import com.example.kottabi.services.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eleve")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @PostMapping
    public Eleve ajouterEleve(@RequestBody Eleve eleve){
      return  eleveService.ajouterEleve(eleve);
    }
}
