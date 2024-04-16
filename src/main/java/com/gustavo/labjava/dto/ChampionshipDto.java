package com.gustavo.labjava.dto;

import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChampionshipDto {

    Long id;
    private Integer year;
    private String place;
    private Set<Long> playerIds;
}
