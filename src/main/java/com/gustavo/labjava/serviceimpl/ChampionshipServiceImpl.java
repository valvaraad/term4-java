package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.aspect.Logger;
import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.exception.BadRequestException;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.mapper.ChampionshipMapper;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.repository.*;
import com.gustavo.labjava.service.ChampionshipService;
import com.gustavo.labjava.utils.cache.GenericCache;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final ChampionshipMapper championshipMapper;
    private GenericCache<Long, Championship> championshipCache;
    private final PlayerRepository playerRepository;

    @Autowired
    public ChampionshipServiceImpl(ChampionshipRepository championshipRepository,
                                   PlayerRepository playerRepository,
                                   GenericCache<Long, Championship> championshipCache)  {
        this.championshipRepository = championshipRepository;
        this.championshipMapper = new ChampionshipMapper(playerRepository);
        this.playerRepository = playerRepository;
        this.championshipCache = championshipCache;
    }
    @Override
    @Logger
    public ChampionshipDto createChampionship(ChampionshipDto championshipDto) {
        if (championshipDto.getYear() < 1800 || championshipDto.getPlace().isEmpty()) {
            throw new BadRequestException("Wrong championship parameters");
        }

        Championship championship = championshipMapper.mapToChampionship(championshipDto);
        Championship savedChampionship = championshipRepository.save(championship);
        return championshipMapper.mapToChampionshipDto(savedChampionship);
    }

    @Override
    @Logger
    public ChampionshipDto getChampionshipById(Long championshipId) {
        Championship championship = championshipCache.get(championshipId).orElseGet(() -> championshipRepository.findById(championshipId).orElseThrow(() ->
                new ResourceNotFoundException("Championship with ID " + championshipId + " does not exist.")));
        return championshipMapper.mapToChampionshipDto(championship);
    }

    @Override
    @Logger
    public List<ChampionshipDto> getAllChampionships() {
        List<Championship> championships = championshipRepository.findAll();
        return championships.stream().map(championshipMapper::mapToChampionshipDto).toList();
    }

    @Override
    @Logger
    public ChampionshipDto updateChampionship(Long championshipId, ChampionshipDto updateChampionship) {

        if (updateChampionship.getYear() < 1800 || updateChampionship.getPlace().isEmpty()) {
            throw new BadRequestException("Wrong championship parameters");
        }

        Championship championship = championshipMapper.mapToChampionship(updateChampionship);
        championship.setId(championshipId);

        Championship updatedChampionshipObj = championshipRepository.save(championship);
        return championshipMapper.mapToChampionshipDto(updatedChampionshipObj);
    }

    @Override
    @Logger
    @Transactional
    public void deleteChampionship(Long championshipId) {
        Championship championship = championshipRepository.findById(championshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Championship not found with id " + championshipId));

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

        championshipCache.remove(championshipId);
        championshipRepository.delete(championship);
    }

    @Override
    @Logger
    public ChampionshipDto getChampionshipByYear(Integer year) {
        Championship championship = championshipRepository.findByYear(year).orElseThrow(() ->
                new ResourceNotFoundException("No championship took place in " + year + "."));
        return championshipMapper.mapToChampionshipDto(championship);
    }
}
