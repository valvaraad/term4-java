package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.aspect.Logger;
import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.exception.*;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.mapper.PlayerMapper;
import com.gustavo.labjava.repository.*;
import com.gustavo.labjava.service.PlayerService;
import com.gustavo.labjava.utils.cache.*;
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
    private GenericCache<Long, Player> playerCache;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             CountryRepository countryRepository,
                             ChampionshipRepository championshipRepository,
                             GenericCache<Long, Player> playerCache) {
        this.playerRepository = playerRepository;
        this.countryRepository = countryRepository;
        this.championshipRepository = championshipRepository;
        this.playerMapper = new PlayerMapper(countryRepository, championshipRepository);
        this.playerCache = playerCache;
    }

    @Override
    @Logger
    public PlayerDto createPlayer(PlayerDto playerDto) {
        if (playerDto.getName().isEmpty() || playerDto.getUsername().isEmpty()) {
            throw new BadRequestException("Wrong player parameters");
        }

        Player player = playerMapper.mapToPlayer(playerDto);
        Player savedPlayer = playerRepository.save(player);
        return playerMapper.mapToPlayerDto(savedPlayer);
    }

    @Override
    @Logger
    public PlayerDto getPlayerById(Long playerId) {

        Player player = playerCache.get(playerId).orElseGet(() -> playerRepository.findById(playerId).orElseThrow(() ->
                new ResourceNotFoundException("Player with ID " + playerId + " does not exist.")));
        playerCache.put(playerId, player);
        return playerMapper.mapToPlayerDto(player);
    }

    @Override
    @Logger
    public List<PlayerDto> getAllPlayers() {

        List<Player> players =  playerRepository.findAll();
        return players.stream().map(playerMapper::mapToPlayerDto)
                .toList();
    }

    @Override
    @Logger
    public PlayerDto updatePlayer(Long playerId, PlayerDto updatedPlayer) {

        if (updatedPlayer.getUsername().isEmpty() || updatedPlayer.getName().isEmpty()) {
            throw new BadRequestException("Wrong player parameters");
        }

        Player player = playerCache.get(playerId).orElseGet(() -> playerRepository.findById(playerId).orElseThrow(() ->
                new ResourceNotFoundException("Player with ID " + playerId + " does not exist.")));

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

        playerCache.remove(playerId);

        return playerMapper.mapToPlayerDto(updatedPlayerObj);
    }

    @Override
    @Logger
    public void deletePlayer(Long playerId) {

        if (playerRepository.findById(playerId).isEmpty()) {
            throw new ResourceNotFoundException("There is no player with given ID: " + playerId);
        }

        playerCache.remove(playerId);
        playerRepository.deleteById(playerId);
    }



}
