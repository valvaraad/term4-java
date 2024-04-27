package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.service.CounterService;
import com.gustavo.labjava.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PlayerControllerTests {

  @InjectMocks
  PlayerController playerController;

  @Mock
  PlayerService playerService;

  @Mock
  private CounterService requestCounter;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllPlayers() {
    List<PlayerDto> playerDtos = Arrays.asList(new PlayerDto(), new PlayerDto());
    when(playerService.getAllPlayers()).thenReturn(playerDtos);
    doNothing().when(requestCounter).increment();

    ResponseEntity<List<PlayerDto>> response = playerController.getAllPlayers();

    assertEquals(playerDtos, response.getBody());
  }

  @Test
  void testGetPlayerById() {
    PlayerDto playerDto = new PlayerDto();
    when(playerService.getPlayerById(1L)).thenReturn(playerDto);
    doNothing().when(requestCounter).increment();

    ResponseEntity<PlayerDto> response = playerController.getPlayerById(1L);

    assertEquals(playerDto, response.getBody());
  }

  @Test
  void testCreatePlayer() {
    PlayerDto playerDto = new PlayerDto();
    when(playerService.createPlayer(playerDto)).thenReturn(playerDto);
    doNothing().when(requestCounter).increment();

    ResponseEntity<PlayerDto> response = playerController.createPlayer(playerDto);

    assertEquals(playerDto, response.getBody());
  }

  @Test
  void testCreatePlayers() {
    List<PlayerDto> playerDtos = Arrays.asList(new PlayerDto(), new PlayerDto());
    when(playerService.createPlayers(playerDtos)).thenReturn(playerDtos);
    doNothing().when(requestCounter).increment();

    ResponseEntity<List<PlayerDto>> response = playerController.createPlayers(playerDtos);

    assertEquals(playerDtos, response.getBody());
  }

  @Test
  void testUpdatePlayer() {
    PlayerDto updatedPlayerDto = new PlayerDto();
    when(playerService.updatePlayer(1L, updatedPlayerDto)).thenReturn(updatedPlayerDto);
    doNothing().when(requestCounter).increment();

    ResponseEntity<PlayerDto> response = playerController.updatePlayer(1L, updatedPlayerDto);

    assertEquals(updatedPlayerDto, response.getBody());
  }

  @Test
  void testDeletePlayer() {
    Long playerId = 1L;
    doNothing().when(playerService).deletePlayer(playerId);
    doNothing().when(requestCounter).increment();

    ResponseEntity<String> response = playerController.deletePlayer(playerId);

    assertEquals("Player deleted.", response.getBody());
  }
}