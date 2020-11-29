package dev.personal.springapi.services;

import dev.personal.springapi.entities.PoliticalParty;
import dev.personal.springapi.repos.PoliticalPartyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticalPartyService {

    @Autowired
    private PoliticalPartyRepo repo;

    public PoliticalParty getOne(Integer id) {
        return repo.findById(id).orElse(new PoliticalParty());
    }

    public List<PoliticalParty> findAll() {
        return repo.findAll();
    }

    public boolean save(PoliticalParty politicalParty) {
        return repo.save(politicalParty) != null;
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
