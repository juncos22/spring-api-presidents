package dev.personal.springapi.repos;

import dev.personal.springapi.entities.PresidentPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresidentPhotoRepo extends JpaRepository<PresidentPhoto, Long> {
}
