package com.example.kottabi.services.ServiceImp;

import com.example.kottabi.models.Eleve;
import com.example.kottabi.repositories.EleveRepo;
import com.example.kottabi.services.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EleveServiceImpl implements EleveService {


    private final EleveRepo eleveRepo;

    public EleveServiceImpl(EleveRepo eleveRepo) {
        this.eleveRepo = eleveRepo;
    }

    @Override
    public Eleve ajouterEleve(Eleve eleve) {
        return eleveRepo.save(eleve);
    }
}
