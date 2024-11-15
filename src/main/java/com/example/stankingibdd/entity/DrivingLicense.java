package com.example.stankingibdd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "driving_license")
public class DrivingLicense {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "license_number", nullable = false, unique = true)
    private UUID licenseNumber;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "department_code", nullable = false, length = 45)
    private String departmentCode;

    @OneToOne
    @JoinColumn(name = "client_number", referencedColumnName = "client_number")
    private Client client;

    @OneToMany(mappedBy = "drivingLicense", cascade = CascadeType.ALL)
    private List<DrivingLicenseCategory> drivingLicenseCategories;
}