package com.gustavo.labjava.controller;

import com.gustavo.labjava.service.ChessService;
import org.springframework.web.bind.annotation.*;
import lombok.Data;

import com.gustavo.labjava.model.PlayerResponse;

@Data
@RestController
public class ChessController {

    private final ChessService chessService;

    @GetMapping("/player")
    public PlayerResponse getPlayer(@RequestParam("username") String username) {
        return chessService.getStats(username);
    }

}
