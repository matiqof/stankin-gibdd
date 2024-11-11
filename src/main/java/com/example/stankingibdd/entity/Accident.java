package com.example.stankingibdd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accident")
public class Accident {

    @Id
    @Column(name = "accident_number", nullable = false)
    private UUID accidentNumber;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "location", nullable = false, length = 256)
    private String location;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "accident", cascade = CascadeType.ALL)
    private List<AccidentComposition> accidentCompositions;
}
