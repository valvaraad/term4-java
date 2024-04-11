package com.gustavo.labjava.dto;

import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    Long id;
    private String name;
    private String code;
    private Set<Long> playerIds;
}
