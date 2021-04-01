package dev.personal.springapi.entities;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Data
public class President {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    private PoliticalParty politicalParty;
    private int votes;
    @OneToOne
    private PresidentPhoto presidentPhoto;
}
