package com.example.stankingibdd.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class DrivingLicenseCategoryId implements Serializable {

    private UUID licenseId;
    private UUID categoryId;
}
