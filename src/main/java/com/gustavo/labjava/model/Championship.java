package com.gustavo.labjava.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"championships\"")
public class Championship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "place")
    private String place;

    @ManyToMany(mappedBy = "championships")
    private List<Player> players;
}
