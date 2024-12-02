package com.example.stankingibdd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accident")
public class Accident {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "accident_id", nullable = false, unique = true)
    private UUID accidentId;

    @Column(name = "time", nullable = false)
    private Date time;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "accident", cascade = CascadeType.ALL)
    private List<AccidentComposition> accidentCompositions;
}
