package com.gustavo.labjava.service;

import com.gustavo.labjava.model.PlayerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChessService {
    private ChessService() {}
    public PlayerResponse getStats(String username) {
        String apiUrl = "https://api.chess.com/pub/player/" + username;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, PlayerResponse.class);
    }

}
