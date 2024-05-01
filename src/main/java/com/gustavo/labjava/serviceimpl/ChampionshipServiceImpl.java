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
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

  private final ChampionshipRepository championshipRepository;
  private final ChampionshipMapper championshipMapper;
  private final GenericCache<Long, Championship> championshipCache;
  private final PlayerRepository playerRepository;
  String wrongParameters = "Wrong championship parameters";

  @Autowired
  public ChampionshipServiceImpl(ChampionshipRepository championshipRepository,
                                 PlayerRepository playerRepository,
                                 GenericCache<Long, Championship> championshipCache) {
    this.championshipRepository = championshipRepository;
    this.championshipMapper = new ChampionshipMapper(playerRepository);
    this.playerRepository = playerRepository;
    this.championshipCache = championshipCache;
  }

  @Override
  @Logger
  public ChampionshipDto createChampionship(ChampionshipDto championshipDto)
      throws BadRequestException {
    if (championshipDto.getYear() < 1800 || championshipDto.getPlace().isEmpty()) {
      throw new BadRequestException(wrongParameters);
    }


    Championship championship = championshipMapper.mapToChampionship(championshipDto);
    Championship savedChampionship = championshipRepository.save(championship);
    return championshipMapper.mapToChampionshipDto(savedChampionship);
  }

  @Override
  @Logger
  public List<ChampionshipDto> createChampionships(List<ChampionshipDto> championshipDtos)
      throws BadRequestException {
    if (championshipDtos.stream().anyMatch(c -> (c.getPlace().isEmpty() || c.getYear() < 1800))) {
      throw new BadRequestException(wrongParameters);
    }

    return championshipDtos.stream()
        .map(c -> championshipRepository.save(championshipMapper.mapToChampionship(c)))
        .map(championshipMapper::mapToChampionshipDto).toList();
  }


  @Override
  @Logger
  public ChampionshipDto getChampionshipById(Long championshipId) throws ResourceNotFoundException {
    Championship championship = championshipCache.get(championshipId)
        .orElseGet(() -> championshipRepository.findById(championshipId).orElseThrow(() ->
            new ResourceNotFoundException(
                "Championship with ID " + championshipId + " does not exist.")));
    championshipCache.put(championshipId, championship);
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
  public ChampionshipDto updateChampionship(Long championshipId,
                                            ChampionshipDto updateChampionship)
      throws ResourceNotFoundException, BadRequestException {

    if (updateChampionship.getYear() < 1800 || updateChampionship.getPlace().isEmpty()) {
      throw new BadRequestException(wrongParameters);
    }

    Championship championship = championshipRepository.findById(championshipId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Championship with ID " + championshipId + " does not exist."));

    championship.setId(championshipId);
    championship.setYear(updateChampionship.getYear());
    championship.setPlace(updateChampionship.getPlace());

    Championship updatedChampionshipObj = championshipRepository.save(championship);
    return championshipMapper.mapToChampionshipDto(updatedChampionshipObj);
  }

  @Override
  @Logger
  @Transactional
  public void deleteChampionship(Long championshipId) throws ResourceNotFoundException {
    Championship championship = championshipRepository.findById(championshipId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Championship not found with id " + championshipId));

    if (championship.getPlayers() != null) {
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
    }

    championshipCache.remove(championshipId);
    championshipRepository.deleteById(championshipId);
  }

  @Override
  @Logger
  public ChampionshipDto getChampionshipByYear(Integer year) throws ResourceNotFoundException {
    Championship championship = championshipRepository.findByYear(year).orElseThrow(() ->
        new ResourceNotFoundException("No championship took place in " + year + "."));
    return championshipMapper.mapToChampionshipDto(championship);
  }
}
