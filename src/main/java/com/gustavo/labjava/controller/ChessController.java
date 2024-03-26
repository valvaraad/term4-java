package com.gustavo.labjava.controller;

import com.gustavo.labjava.service.ChessService;
import org.springframework.web.bind.annotation.*;

import com.gustavo.labjava.model.PlayerResponse;

@RestController
public class ChessController {

    @RequestMapping("/player")
    public PlayerResponse getPlayer(@RequestParam("username") String username) {
        return ChessService.getStats(username);
    }

}
