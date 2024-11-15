package com.example.stankingibdd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name = "persistent_logins")
@NoArgsConstructor
@AllArgsConstructor
public class PersistentLogin {

    @Column(name = "username", nullable = false)
    private String username;

    @Id
    @Column(name = "series", nullable = false)
    private UUID series;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Date lastUsed;
}