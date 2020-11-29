package dev.personal.springapi.repos;

import dev.personal.springapi.entities.PoliticalParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticalPartyRepo extends JpaRepository<PoliticalParty, Integer> {

}
