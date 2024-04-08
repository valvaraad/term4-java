package com.gustavo.labjava.service;

import com.gustavo.labjava.model.Player;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlayerService {
    private PlayerService() {}
    public Player getStats(String username) {
        String apiUrl = "https://api.chess.com/pub/player/" + username;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, Player.class);
    }

}
