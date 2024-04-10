package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.model.Player;
import com.gustavo.labjava.mapper.PlayerMapper;
import com.gustavo.labjava.repository.PlayerRepository;
import com.gustavo.labjava.service.PlayerService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public PlayerDto createPlayer(PlayerDto playerDto) {

        Player player = PlayerMapper.mapToPlayer(playerDto);
        Player savedPlayer = playerRepository.save(player);
        return PlayerMapper.mapToPlayerDto(savedPlayer);
    }

    @Override
    public PlayerDto getPlayerById(Long playerId) {

        Player player = playerRepository.findById(playerId).orElseThrow(() ->
                new ResourceNotFoundException("Player with ID " + playerId + " does not exist."));
        return PlayerMapper.mapToPlayerDto(player);
    }

    @Override
    public List<PlayerDto> getAllPlayers() {

        List<Player> players =  playerRepository.findAll();
        return players.stream().map((player) -> PlayerMapper.mapToPlayerDto(player))
                .toList();
    }

    @Override
    public PlayerDto updatePlayer(Long playerId, PlayerDto updatedPlayer) {

        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("There is no player with given ID: " + playerId));

        player.setUsername(updatedPlayer.getUsername());
        player.setName(updatedPlayer.getName());
        player.setTitle(updatedPlayer.getTitle());
        player.setCountry(updatedPlayer.getCountry());

        Player updatedPlayerObj = playerRepository.save(player);

        return PlayerMapper.mapToPlayerDto(updatedPlayerObj);
    }

    @Override
    public void deletePlayer(Long playerId) {

        playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("There is no player with given ID: " + playerId));

        playerRepository.deleteById(playerId);

    }


}
