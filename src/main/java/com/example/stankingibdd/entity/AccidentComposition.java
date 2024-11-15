package com.example.stankingibdd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "accident_composition")
public class AccidentComposition {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "accident_number", nullable = false, unique = true)
    private UUID accidentNumber;

    @Column(name = "vehicle_number", nullable = false)
    private UUID vehicleNumber;

    @ManyToOne
    @JoinColumn(name = "accident_number", referencedColumnName = "accident_number", insertable = false, updatable = false)
    private Accident accident;

    @ManyToOne
    @JoinColumn(name = "vehicle_number", referencedColumnName = "vehicle_number", insertable = false, updatable = false)
    private Vehicle vehicle;
}