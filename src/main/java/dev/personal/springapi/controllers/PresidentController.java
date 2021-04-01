package dev.personal.springapi.controllers;

import dev.personal.springapi.entities.PoliticalParty;
import dev.personal.springapi.entities.President;
import dev.personal.springapi.entities.PresidentPhoto;
import dev.personal.springapi.services.FileService;
import dev.personal.springapi.services.PoliticalPartyService;
import dev.personal.springapi.services.PresidentPhotoService;
import dev.personal.springapi.services.PresidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/presidents")
public class PresidentController {
    private final PresidentService service;
    private final PresidentPhotoService photoService;
    private final PoliticalPartyService partyService;
    private final FileService fileService;

    @Autowired
    public PresidentController(PresidentService service, FileService fileService,
                               PresidentPhotoService photoService, PoliticalPartyService partyService) {
        this.service = service;
        this.fileService = fileService;
        this.photoService = photoService;
        this.partyService = partyService;
    }

    @GetMapping
    public ResponseEntity<List<President>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // FIND PRESIDENT BY ID -> localhost:
    @GetMapping("/{id}")
    public ResponseEntity<President> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    // FIND PRESIDENT BY POLITICAL PARTY ID
    @GetMapping("/find-party/{politicalPartyId}")
    public ResponseEntity<List<President>> findParty(@PathVariable Integer politicalPartyId) {
        return ResponseEntity.ok(service.findByPoliticalPartyId(politicalPartyId));
    }

    @PostMapping("/save")
    public ResponseEntity<President> save(@RequestParam("name") String name,
                                        @RequestParam("politicalParty") String politicalParty,
                                        @RequestParam("votes") int votes,
                                        @RequestParam("photo")MultipartFile photo) {

        String fileName = fileService.storeFile(photo);
        String contentType = photo.getContentType();
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        President president = new President();
        president.setName(name);
        president.setPoliticalParty(partyService.findByName(politicalParty));
        president.setVotes(votes);

        PresidentPhoto presidentPhoto = new PresidentPhoto();
        presidentPhoto.setFileName(fileName);
        presidentPhoto.setContentType(contentType);
        presidentPhoto.setUrl(url);

        president.setPresidentPhoto(photoService.save(presidentPhoto));
        return ResponseEntity.ok(service.save(president));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable("fileName") String fileName,
                                                  HttpServletRequest request) {
        Resource resource = fileService.downloadFile(fileName);
//        MediaType contentType = MediaType.IMAGE_JPEG;
        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                // View file
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+resource.getFilename())
//    Download file .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.getFilename())
                .body(resource);
    }
//    @PutMapping("/{id}/set")
//    public ResponseEntity<Boolean> set(@PathVariable Integer id, @RequestBody President president) {
//        President p = service.getOne(id);
//        p.setName(president.getName());
//        p.setPoliticalParty(president.getPoliticalParty());
//        p.setVotes(president.getVotes());
//        return ResponseEntity.ok(service.save(p));
//    }
//
//    @DeleteMapping("/{id}/remove")
//    public ResponseEntity<Boolean> remove(@PathVariable Integer id) {
//        service.delete(id);
//        return ResponseEntity.ok(true);
//    }
}
