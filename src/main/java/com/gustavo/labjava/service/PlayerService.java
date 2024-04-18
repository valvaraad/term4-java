package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.PlayerDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {

  PlayerDto createPlayer(PlayerDto playerDto);

  PlayerDto getPlayerById(Long playerId);

  List<PlayerDto> getAllPlayers();

  PlayerDto updatePlayer(Long playerId, PlayerDto updatedPlayer);

  void deletePlayer(Long playerId);
}
