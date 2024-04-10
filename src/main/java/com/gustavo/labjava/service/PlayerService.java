package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.PlayerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {

    PlayerDto createPlayer(PlayerDto playerDto);

    PlayerDto getPlayerById(Long playerId);

    List<PlayerDto> getAllPlayers();

    PlayerDto updatePlayer(Long playerId, PlayerDto updatedPlayer);

    void deletePlayer(Long playerId);
}
