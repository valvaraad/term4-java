package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.model.Player;
import com.gustavo.labjava.mapper.PlayerMapper;
import com.gustavo.labjava.repository.PlayerRepository;
import com.gustavo.labjava.service.PlayerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public PlayerDto createPlayer(PlayerDto playerDto) {

        Player player = PlayerMapper.mapToPlayer(playerDto);
        Player savedPlayer = playerRepository.save(player);
        return PlayerMapper.mapToPlayerDto(savedPlayer);
    }
}
