package com.example.stankingibdd.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AccidentCompositionId implements Serializable {

    private UUID accidentId;
    private UUID vehicleId;
}