package com.example.stankingibdd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "password_reset_token")
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "password_reset_token_number", nullable = false, unique = true)
    private UUID password_reset_token_number;

    @Column(name = "token", nullable = false, unique = true)
    private UUID token;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "client_number", referencedColumnName = "client_number")
    private Client client;

    public boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}