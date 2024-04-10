package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.service.PlayerService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.gustavo.labjava.model.Player;

@NoArgsConstructor
@RestController
@RequestMapping("/players")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/create")
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto) {
        PlayerDto savedPlayer = playerService.createPlayer(playerDto);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable("id") Long playerId) {
        PlayerDto playerDto = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(playerDto);
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> players =  playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @PutMapping("{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable("id") Long playerId,
                                                  @RequestBody PlayerDto updatedPlayer) {
        PlayerDto playerDto = playerService.updatePlayer(playerId, updatedPlayer);
        return ResponseEntity.ok(playerDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable("id") Long playerId) {
        playerService.deletePlayer(playerId);
        return ResponseEntity.ok("Player deleted.");
    }

}
