package com.gustavo.labjava.dto;

import java.util.Set;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

  Long id;
  private String name;
  private String code;
  private Set<Long> playerIds;
}
