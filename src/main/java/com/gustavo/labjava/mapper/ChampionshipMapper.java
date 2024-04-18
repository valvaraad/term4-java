package com.gustavo.labjava.mapper;

import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.repository.PlayerRepository;
import java.util.*;
import java.util.stream.Collectors;

public class ChampionshipMapper {

  final PlayerRepository playerRepository;

  public ChampionshipMapper(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public ChampionshipDto mapToChampionshipDto(Championship championship) {

    List<Player> players = championship.getPlayers();
    Set<Long> ids = null;
    if (players != null) {
      ids = players.stream().map(Player::getId).collect(Collectors.toSet());
    }

    return new ChampionshipDto(
        championship.getId(),
        championship.getYear(),
        championship.getPlace(),
        ids
    );
  }

  public Championship mapToChampionship(ChampionshipDto championshipDto) {

    Set<Long> ids = championshipDto.getPlayerIds();
    List<Player> players = null;

    if (ids != null && this.playerRepository != null) {
      players = this.playerRepository.findAllById(ids);
    }


    return new Championship(
        championshipDto.getId(),
        championshipDto.getYear(),
        championshipDto.getPlace(),
        players
    );
  }
}
