package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.service.CounterService;
import com.gustavo.labjava.service.PlayerService;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@NoArgsConstructor
@RestController
@RequestMapping("/players")
public class PlayerController {

  private PlayerService playerService;
  private CounterService requestCounter;

  @Autowired
  public PlayerController(PlayerService playerService, CounterService requestCounter) {
    this.playerService = playerService;
    this.requestCounter = requestCounter;
  }

  @PostMapping("/create")
  public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto) {
    requestCounter.increment();
    PlayerDto savedPlayer = playerService.createPlayer(playerDto);
    return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
  }

  @PostMapping("/createbulk")
  public ResponseEntity<List<PlayerDto>> createPlayers(@RequestBody List<PlayerDto> playerDtos) {
    requestCounter.increment();
    List<PlayerDto> savedPlayers = playerService.createPlayers(playerDtos);
    return new ResponseEntity<>(savedPlayers, HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public ResponseEntity<PlayerDto> getPlayerById(@PathVariable("id") Long playerId) {
    requestCounter.increment();
    PlayerDto playerDto = playerService.getPlayerById(playerId);
    return ResponseEntity.ok(playerDto);
  }

  @GetMapping
  public ResponseEntity<List<PlayerDto>> getAllPlayers() {
    requestCounter.increment();
    List<PlayerDto> players = playerService.getAllPlayers();
    return ResponseEntity.ok(players);
  }

  @PutMapping("{id}")
  public ResponseEntity<PlayerDto> updatePlayer(@PathVariable("id") Long playerId,
                                                @RequestBody PlayerDto updatedPlayer) {
    requestCounter.increment();
    PlayerDto playerDto = playerService.updatePlayer(playerId, updatedPlayer);
    return ResponseEntity.ok(playerDto);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deletePlayer(@PathVariable("id") Long playerId) {
    requestCounter.increment();
    playerService.deletePlayer(playerId);
    return ResponseEntity.ok("Player deleted.");
  }

}
