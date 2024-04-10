package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.model.Player;
import com.gustavo.labjava.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public interface PlayerService {

    PlayerDto createPlayer(PlayerDto playerDto);

    PlayerDto getPlayerById(Long playerId);

    List<PlayerDto> getAllPlayers();

    PlayerDto updatePlayer(Long playerId, PlayerDto updatedPlayer);

    void deletePlayer(Long playerId);
}
