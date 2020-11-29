package dev.personal.springapi.services;

import dev.personal.springapi.entities.PoliticalParty;
import dev.personal.springapi.entities.President;
import dev.personal.springapi.repos.PoliticalPartyRepo;
import dev.personal.springapi.repos.PresidentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PresidentService {

    @Autowired
    private PresidentRepo repo;
    @Autowired
    private PoliticalPartyRepo politicalPartyRepo;

    public President getOne(Integer id) {
        return repo.findById(id).orElse(new President());
    }

    public List<President> findAll() {
        return repo.findAll();
    }

    public boolean save(President president) {
        return repo.save(president) != null;
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public List<President> findByPoliticalPartyId(Integer politicalPartyId) {
        Optional<PoliticalParty> politicalParty = politicalPartyRepo.findById(politicalPartyId);
        return politicalParty.isPresent() ? repo.findPresidentByPoliticalParty(politicalParty.get()) :
                new ArrayList<>();
    }
}
