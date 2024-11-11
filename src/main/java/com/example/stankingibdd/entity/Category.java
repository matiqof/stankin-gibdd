package com.example.stankingibdd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_number", nullable = false)
    private UUID categoryNumber;

    @Column(name = "category_name", nullable = false, length = 45)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<DrivingLicenseCategory> drivingLicenseCategories;
}