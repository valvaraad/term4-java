package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.mapper.ChampionshipMapper;
import com.gustavo.labjava.model.Championship;
import com.gustavo.labjava.repository.*;
import com.gustavo.labjava.service.ChampionshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionshipServiceImpl implements ChampionshipService {

    private final ChampionshipRepository championshipRepository;
    private final ChampionshipMapper championshipMapper;

    @Autowired
    public ChampionshipServiceImpl(ChampionshipRepository championshipRepository, PlayerRepository playerRepository)  {
        this.championshipRepository = championshipRepository;
        this.championshipMapper = new ChampionshipMapper(playerRepository);
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
    public void deleteChampionship(Long championshipId) {

        if (championshipRepository.findById(championshipId).isEmpty()) {
            throw new ResourceNotFoundException("There is no championship with given ID: " + championshipId);
        }

        championshipRepository.deleteById(championshipId);
    }
}
