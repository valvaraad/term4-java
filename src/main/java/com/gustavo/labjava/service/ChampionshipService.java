package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.ChampionshipDto;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public interface ChampionshipService {

  ChampionshipDto createChampionship(ChampionshipDto championshipDto);

  ChampionshipDto getChampionshipById(Long championshipId);

  List<ChampionshipDto> getAllChampionships();

  ChampionshipDto updateChampionship(Long championshipId, ChampionshipDto updateChampionship);

  void deleteChampionship(Long championshipId);

  ChampionshipDto getChampionshipByYear(Integer year);

}
