package com.example.stankingibdd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "fine")
public class Fine {

    @Id
    @Column(name = "fine_number", nullable = false)
    private UUID fineNumber;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "location", nullable = false, length = 45)
    private String location;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "type", nullable = false, length = 45)
    private String type;

    @Column(name = "description", length = 45)
    private String description;

    @Column(name = "article", nullable = false, length = 45)
    private String article;

    @ManyToOne
    @JoinColumn(name = "vehicle_number", referencedColumnName = "vehicle_number")
    private Vehicle vehicle;
}