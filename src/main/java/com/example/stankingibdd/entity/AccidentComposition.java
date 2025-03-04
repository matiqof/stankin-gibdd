package com.example.stankingibdd.entity;

import com.example.stankingibdd.model.AccidentCompositionId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accident_composition")
@IdClass(AccidentCompositionId.class)
public class AccidentComposition {

    @Id
    @Column(name = "accident_id", nullable = false, unique = true)
    private UUID accidentId;

    @Id
    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @ManyToOne
    @JoinColumn(name = "accident_id", referencedColumnName = "accident_id", insertable = false, updatable = false)
    private Accident accident;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id", insertable = false, updatable = false)
    private Vehicle vehicle;
}