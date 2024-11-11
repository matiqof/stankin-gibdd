package com.example.stankingibdd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "driving_license_category")
public class DrivingLicenseCategory {

    @Id
    @Column(name = "license_number", nullable = false)
    private UUID licenseNumber;

    @Column(name = "category_number", nullable = false, unique = true)
    private int categoryNumber;

    @ManyToOne
    @JoinColumn(name = "license_number", referencedColumnName = "license_number", insertable = false, updatable = false)
    private DrivingLicense drivingLicense;

    @ManyToOne
    @JoinColumn(name = "category_number", referencedColumnName = "category_number", insertable = false, updatable = false)
    private Category category;
}