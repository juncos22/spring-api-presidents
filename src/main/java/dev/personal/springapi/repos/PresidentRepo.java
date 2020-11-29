package dev.personal.springapi.repos;

import dev.personal.springapi.entities.PoliticalParty;
import dev.personal.springapi.entities.President;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresidentRepo extends JpaRepository<President, Integer> {

    List<President> findPresidentByPoliticalParty(PoliticalParty politicalParty);
}
