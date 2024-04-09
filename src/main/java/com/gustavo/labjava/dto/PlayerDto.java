package com.gustavo.labjava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    Long id;
    private String username;
    private String title;
    private String name;
    private String country;
}
