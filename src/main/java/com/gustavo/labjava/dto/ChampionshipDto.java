package com.gustavo.labjava.dto;

import java.util.Set;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChampionshipDto {

  Long id;
  private Integer year;
  private String place;
  private Set<Long> playerIds;
}
