package dev.personal.springapi.services;

import dev.personal.springapi.entities.PresidentPhoto;
import dev.personal.springapi.repos.PresidentPhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresidentPhotoService {
    private final PresidentPhotoRepo photoRepo;

    @Autowired
    public PresidentPhotoService(PresidentPhotoRepo photoRepo) {
        this.photoRepo = photoRepo;
    }

    public PresidentPhoto save(PresidentPhoto presidentPhoto) {
        return photoRepo.save(presidentPhoto);
    }
}
