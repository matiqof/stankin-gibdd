package com.example.stankingibdd.entity;

import com.example.stankingibdd.model.ClientRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "client_id", nullable = false, unique = true)
    private UUID clientId;

    @Column(name = "license_id")
    private UUID licenseId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "phone", nullable = false, length = 11, unique = true)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "passport_number", nullable = false, length = 10, unique = true)
    private String passportNumber;

    @Column(name = "passport_issue_date", nullable = false)
    private Date passportIssueDate;

    @Column(name = "passport_department_code", nullable = false)
    private int passportDepartmentCode;

    @Column(name = "password", nullable = false, length = 60)
    private String clientPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private ClientRole role;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private DrivingLicense drivingLicense;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(getRole());
    }

    @Override
    public String getPassword() {
        return getClientPassword();
    }

    @Override
    public String getUsername() {
        return getPhone();
    }
}