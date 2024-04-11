package com.gustavo.labjava.mapper;

import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.repository.PlayerRepository;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class CountryMapper {

    PlayerRepository playerRepository;
    public CountryMapper(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public CountryDto mapToCountryDto(Country country) {

        List<Player> players = country.getPlayers();
        Set<Long> ids = null;

        if (players != null)
            ids = players.stream().map(Player::getId).collect(Collectors.toSet());


        return new CountryDto(
                country.getId(),
                country.getName(),
                country.getCode(),
                ids
        );
    }

    public Country mapToCountry(CountryDto countryDto) {

        Set<Long> ids = countryDto.getPlayerIds();
        List<Player> players = null;

        if (ids != null && this.playerRepository != null)
            players = this.playerRepository.findAllById(ids);

        return new Country(
                countryDto.getId(),
                countryDto.getName(),
                countryDto.getCode(),
                players
        );
    }
}
