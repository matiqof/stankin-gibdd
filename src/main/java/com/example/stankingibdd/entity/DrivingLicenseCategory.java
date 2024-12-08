package com.example.stankingibdd.entity;

import com.example.stankingibdd.model.DrivingLicenseCategoryId;
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
@Table(name = "driving_license_category")
@IdClass(DrivingLicenseCategoryId.class)
public class DrivingLicenseCategory {

    @Id
    @Column(name = "license_id", nullable = false, unique = true)
    private UUID licenseId;

    @Id
    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    @ManyToOne
    @JoinColumn(name = "license_id", referencedColumnName = "license_id", insertable = false, updatable = false)
    private DrivingLicense drivingLicense;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category category;
}