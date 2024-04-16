package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.ChampionshipDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChampionshipService {

    ChampionshipDto createChampionship(ChampionshipDto championshipDto);

    ChampionshipDto getChampionshipById(Long championshipId);

    List<ChampionshipDto> getAllChampionships();

    ChampionshipDto updateChampionship(Long championshipId, ChampionshipDto updateChampionship);

    void deleteChampionship(Long championshipId);

}
