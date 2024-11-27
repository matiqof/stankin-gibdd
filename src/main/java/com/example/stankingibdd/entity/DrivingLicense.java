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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "driving_license")
@NoArgsConstructor
@AllArgsConstructor
public class DrivingLicense {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "license_id", nullable = false, unique = true)
    private UUID licenseId;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "department_code", nullable = false, length = 45)
    private String departmentCode;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToMany(mappedBy = "drivingLicense", cascade = CascadeType.ALL)
    private List<DrivingLicenseCategory> drivingLicenseCategories;
}