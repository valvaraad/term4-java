package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.mapper.PlayerMapper;
import com.gustavo.labjava.repository.*;
import com.gustavo.labjava.service.PlayerService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Data
@NoArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private CountryRepository countryRepository;
    private ChampionshipRepository championshipRepository;
    private PlayerMapper playerMapper;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             CountryRepository countryRepository,
                             ChampionshipRepository championshipRepository) {
        this.playerRepository = playerRepository;
        this.countryRepository = countryRepository;
        this.championshipRepository = championshipRepository;
        this.playerMapper = new PlayerMapper(countryRepository, championshipRepository);
    }

    @Override
    public PlayerDto createPlayer(PlayerDto playerDto) {

        Player player = playerMapper.mapToPlayer(playerDto);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.mapToPlayerDto(savedPlayer);
    }

    @Override
    public PlayerDto getPlayerById(Long playerId) {

        Player player = playerRepository.findById(playerId).orElseThrow(() ->
                new ResourceNotFoundException("Player with ID " + playerId + " does not exist."));
        return playerMapper.mapToPlayerDto(player);
    }

    @Override
    public List<PlayerDto> getAllPlayers() {

        List<Player> players =  playerRepository.findAll();
        return players.stream().map(playerMapper::mapToPlayerDto)
                .toList();
    }

    @Override
    public PlayerDto updatePlayer(Long playerId, PlayerDto updatedPlayer) {

        Player player = playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("There is no player with given ID: " + playerId));

        player.setUsername(updatedPlayer.getUsername());
        player.setName(updatedPlayer.getName());
        Country country = countryRepository.findById(updatedPlayer.getCountryId()).orElseThrow(
                () -> new ResourceNotFoundException("There is no country with given ID: " + updatedPlayer.getCountryId())
        );

        player.setCountry(country);

        Set<Long> champIds = updatedPlayer.getChampionshipIds();
        List<Championship> championships = null;
        if (champIds != null)
            championships = championshipRepository.findAllById(champIds);

        player.setChampionships(championships);

        Player updatedPlayerObj = playerRepository.save(player);

        return playerMapper.mapToPlayerDto(updatedPlayerObj);
    }

    @Override
    public void deletePlayer(Long playerId) {

        playerRepository.findById(playerId).orElseThrow(
                () -> new ResourceNotFoundException("There is no player with given ID: " + playerId));

        playerRepository.deleteById(playerId);

    }


}
