package com.example.stankingibdd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @Column(name = "vehicle_number", nullable = false)
    private UUID vehicleNumber;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @Column(name = "manufacturer", nullable = false, length = 45)
    private String manufacturer;

    @Column(name = "year_of_manufacture", nullable = false)
    private int yearOfManufacture;

    @Column(name = "color", nullable = false, length = 45)
    private String color;

    @Column(name = "mileage", nullable = false)
    private int mileage;

    @Column(name = "engine_volume", nullable = false)
    private float engineVolume;

    @Column(name = "horsepower", nullable = false)
    private int horsepower;

    @Column(name = "registration_number", nullable = false, length = 45, unique = true)
    private String registrationNumber;

    @Column(name = "registration_date", nullable = false)
    private Date registrationDate;

    @Column(name = "registration_location", length = 45)
    private String registrationLocation;

    @ManyToOne
    @JoinColumn(name = "client_number", referencedColumnName = "client_number")
    private Client client;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<AccidentComposition> accidentCompositions;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Fine> fines;
}