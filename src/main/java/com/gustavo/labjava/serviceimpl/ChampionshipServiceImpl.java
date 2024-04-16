package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.mapper.ChampionshipMapper;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.repository.*;
import com.gustavo.labjava.service.ChampionshipService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final ChampionshipMapper championshipMapper;

    private final PlayerRepository playerRepository;

    @Autowired
    public ChampionshipServiceImpl(ChampionshipRepository championshipRepository, PlayerRepository playerRepository)  {
        this.championshipRepository = championshipRepository;
        this.championshipMapper = new ChampionshipMapper(playerRepository);
        this.playerRepository = playerRepository;
    }
    @Override
    public ChampionshipDto createChampionship(ChampionshipDto championshipDto) {
        Championship championship = championshipMapper.mapToChampionship(championshipDto);
        Championship savedChampionship = championshipRepository.save(championship);
        return championshipMapper.mapToChampionshipDto(savedChampionship);
    }

    @Override
    public ChampionshipDto getChampionshipById(Long championshipId) {
        Championship championship = championshipRepository.findById(championshipId).orElseThrow(() ->
                new ResourceNotFoundException("Championship with ID " + championshipId + " does not exist."));
        return championshipMapper.mapToChampionshipDto(championship);
    }

    @Override
    public List<ChampionshipDto> getAllChampionships() {
        List<Championship> championships = championshipRepository.findAll();
        return championships.stream().map(championshipMapper::mapToChampionshipDto).toList();
    }

    @Override
    public ChampionshipDto updateChampionship(Long championshipId, ChampionshipDto updateChampionship) {

        Championship championship = championshipMapper.mapToChampionship(updateChampionship);
        championship.setId(championshipId);

        Championship updatedChampionshipObj = championshipRepository.save(championship);
        return championshipMapper.mapToChampionshipDto(updatedChampionshipObj);
    }

    @Override
    @Transactional
    public void deleteChampionship(Long championshipId) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new IllegalArgumentException("Championship not found with id " + championshipId));

        List<Player> playersToDelete = new ArrayList<>();
        championship.getPlayers().forEach(player -> {
            if (player.getChampionships().size() == 1) {
                playersToDelete.add(player);
            } else {
                player.getChampionships().remove(championship);
            }
        });

        playerRepository.saveAll(championship.getPlayers());
        playerRepository.flush();

        playerRepository.deleteAll(playersToDelete);

        championship.getPlayers().clear();
        championshipRepository.delete(championship);
    }
}
