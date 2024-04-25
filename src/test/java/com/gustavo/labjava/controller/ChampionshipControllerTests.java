package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.service.ChampionshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChampionshipControllerTests {

  @InjectMocks
  private ChampionshipController championshipController;

  @Mock
  private ChampionshipService championshipService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateChampionship() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    when(championshipService.createChampionship(championshipDto)).thenReturn(championshipDto);

    ResponseEntity<ChampionshipDto> response = championshipController.createChampionship(championshipDto);

    assertEquals(championshipDto, response.getBody());
  }

  @Test
  void testCreateChampionships() {
    List<ChampionshipDto> championshipDtos = Arrays.asList(new ChampionshipDto(), new ChampionshipDto());
    when(championshipService.createChampionships(championshipDtos)).thenReturn(championshipDtos);

    ResponseEntity<List<ChampionshipDto>> response = championshipController.createChampionships(championshipDtos);

    assertEquals(championshipDtos, response.getBody());
  }

  @Test
  void testGetChampionshipById() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    when(championshipService.getChampionshipById(1L)).thenReturn(championshipDto);

    ResponseEntity<ChampionshipDto> response = championshipController.getChampionshipById(1L);

    assertEquals(championshipDto, response.getBody());
  }

  @Test
  void testGetAllChampionships() {
    List<ChampionshipDto> championshipDtos = Arrays.asList(new ChampionshipDto(), new ChampionshipDto());
    when(championshipService.getAllChampionships()).thenReturn(championshipDtos);

    ResponseEntity<List<ChampionshipDto>> response = championshipController.getAllChampionships();

    assertEquals(championshipDtos, response.getBody());
  }

  @Test
  void testGetChampionshipByYear() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    when(championshipService.getChampionshipByYear(2022)).thenReturn(championshipDto);

    ResponseEntity<ChampionshipDto> response = championshipController.getChampionshipByYear(2022);

    assertEquals(championshipDto, response.getBody());
  }

  @Test
  void testUpdateChampionship() {
    ChampionshipDto originalChampionshipDto = new ChampionshipDto();
    ChampionshipDto updatedChampionshipDto = new ChampionshipDto();
    when(championshipService.updateChampionship(1L, updatedChampionshipDto)).thenReturn(updatedChampionshipDto);

    ResponseEntity<ChampionshipDto> response = championshipController.updateChampionship(1L, updatedChampionshipDto);

    assertEquals(updatedChampionshipDto, response.getBody());
  }

  @Test
  void testDeleteChampionship() {
    Long championshipId = 1L;
    doNothing().when(championshipService).deleteChampionship(championshipId);

    ResponseEntity<String> response = championshipController.deleteChampionship(championshipId);

    assertEquals("Championship deleted.", response.getBody());
  }
}