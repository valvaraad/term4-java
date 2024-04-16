package com.gustavo.labjava.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

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

    @ManyToMany(mappedBy = "championships", cascade = {CascadeType.ALL})
    private List<Player> players;

}
