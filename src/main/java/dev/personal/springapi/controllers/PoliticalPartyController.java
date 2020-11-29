package dev.personal.springapi.controllers;

import dev.personal.springapi.entities.PoliticalParty;
import dev.personal.springapi.services.PoliticalPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/political-party")
public class PoliticalPartyController {

    @Autowired
    private PoliticalPartyService service;

    @GetMapping
    public ResponseEntity<List<PoliticalParty>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // FIND POLITICAL PARTY BY ID -> localhost:8080/political-party/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PoliticalParty> findOne(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }


    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody PoliticalParty politicalParty) {
        return ResponseEntity.ok(service.save(politicalParty));
    }

    @PutMapping("/{id}/set")
    public ResponseEntity<Boolean> set(@PathVariable Integer id, @RequestBody PoliticalParty politicalParty) {
        PoliticalParty pP = service.getOne(id);
        pP.setName(politicalParty.getName());
        return ResponseEntity.ok(service.save(pP));
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<Boolean> remove(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(true);
    }
}
