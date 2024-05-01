package com.gustavo.labjava.mapper;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.repository.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class PlayerMapper {

  final CountryRepository countryRepository;
  final ChampionshipRepository championshipRepository;


  public PlayerMapper(@Autowired CountryRepository countryRepository,
                      @Autowired ChampionshipRepository championshipRepository) {
    this.countryRepository = countryRepository;
    this.championshipRepository = championshipRepository;
  }


  public PlayerDto mapToPlayerDto(Player player) {

    List<Championship> championships = player.getChampionships();
    Set<Long> ids = null;

    if (player.getChampionships() != null) {
      ids = championships.stream().map(Championship::getId).collect(Collectors.toSet());
    }

    return new PlayerDto(
        player.getId(),
        player.getUsername(),
        player.getName(),
        player.getCountry().getId(),
        ids
    );
  }

  public Player mapToPlayer(PlayerDto playerDto) {

    Country country = this.countryRepository.findById(playerDto.getCountryId()).orElseThrow(
        () -> new ResourceNotFoundException(
            "There is no country with given ID: " + playerDto.getCountryId())
    );

    List<Championship> championships = null;

    if (playerDto.getChampionshipIds() != null && this.championshipRepository != null) {
      championships = this.championshipRepository.findAllById(playerDto.getChampionshipIds());
    }



    return new Player(
        playerDto.getId(),
        playerDto.getUsername(),
        playerDto.getName(),
        country,
        championships
    );
  }
}