package com.gustavo.labjava.mapper;

import com.gustavo.labjava.model.Player;
import com.gustavo.labjava.dto.PlayerDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PlayerMapper {

    public static PlayerDto mapToPlayerDto(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getUsername(),
                player.getTitle(),
                player.getName(),
                player.getCountry()
        );
    }

    public static Player mapToPlayer(PlayerDto playerDto) {
        return new Player(
                playerDto.getId(),
                playerDto.getUsername(),
                playerDto.getTitle(),
                playerDto.getName(),
                playerDto.getCountry()
        );
    }
}
