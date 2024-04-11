package com.gustavo.labjava.dto;

import lombok.*;

import java.util.Set;

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
