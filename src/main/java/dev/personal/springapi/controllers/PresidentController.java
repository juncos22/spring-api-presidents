package dev.personal.springapi.controllers;

import dev.personal.springapi.entities.PoliticalParty;
import dev.personal.springapi.entities.President;
import dev.personal.springapi.services.PresidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presidents")
public class PresidentController {
    @Autowired
    private PresidentService service;

    @GetMapping
    public ResponseEntity<List<President>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<President> getOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @GetMapping("/find-party/{politicalPartyId}")
    public ResponseEntity<List<President>> findParty(@PathVariable Integer politicalPartyId) {
        return ResponseEntity.ok(service.findByPoliticalPartyId(politicalPartyId));
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody President president) {
        return ResponseEntity.ok(service.save(president));
    }

    @PutMapping("/{id}/set")
    public ResponseEntity<Boolean> set(@PathVariable Integer id, @RequestBody President president) {
        President p = service.getOne(id);
        p.setName(president.getName());
        p.setPoliticalParty(president.getPoliticalParty());
        p.setVotes(president.getVotes());
        return ResponseEntity.ok(service.save(p));
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<Boolean> remove(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(true);
    }
}
