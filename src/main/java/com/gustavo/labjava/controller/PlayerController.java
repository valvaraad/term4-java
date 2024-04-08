package com.gustavo.labjava.controller;

import com.gustavo.labjava.service.PlayerService;
import org.springframework.web.bind.annotation.*;
import lombok.Data;

import com.gustavo.labjava.model.Player;

@Data
@RestController
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/player")
    public Player getPlayer(@RequestParam("username") String username) {
        return playerService.getStats(username);
    }

}
