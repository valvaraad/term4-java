package com.gustavo.labjava.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Data
@Table(name = "\"countries\"")
@AllArgsConstructor
@NoArgsConstructor
public class Country {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "code")
  private String code;

  @OneToMany(mappedBy = "country", cascade = {CascadeType.ALL})
  private List<Player> players;
}
