package com.gustavo.labjava.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", referencedColumnName = "id")
  private Country country;

  @ManyToMany
  @JoinTable(name = "player_championship",
      inverseJoinColumns = @JoinColumn(name = "championship_id", referencedColumnName = "id"),
      joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
  )
  private List<Championship> championships;
}
