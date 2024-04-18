package com.gustavo.labjava.dto;

import java.util.Set;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

  Long id;
  private String username;
  private String name;
  private Long countryId;
  private Set<Long> championshipIds;
}
