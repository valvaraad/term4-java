package com.gustavo.labjava.service;


import com.gustavo.labjava.dto.PlayerDto;
import com.gustavo.labjava.exception.BadRequestException;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.mapper.PlayerMapper;
import com.gustavo.labjava.model.*;
import com.gustavo.labjava.repository.*;
import com.gustavo.labjava.serviceimpl.PlayerServiceImpl;
import com.gustavo.labjava.utils.cache.GenericCache;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTests {

  @Mock
  private PlayerRepository playerRepository;

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private ChampionshipRepository championshipRepository;

  @Mock
  private GenericCache<Long, Player> playerCache;


  @Mock
  private PlayerMapper playerMapper;

  private Player player;
  private Country country;
  private Championship championship;

  @InjectMocks
  private PlayerServiceImpl playerService;

  @BeforeEach
  public void setUp() {

    player = new Player();
    country = new Country();
    championship = new Championship();

    country.setName("country1");
    country.setCode("C1");
    country.setId(1L);

    championship.setYear(2000);
    championship.setPlace("place1");
    championship.setId(1L);

    player.setName("player1");
    player.setUsername("username1");
    player.setCountry(country);
    player.setChampionships(List.of(championship));
  }

  @Test
  void testGetPlayerByIdFromRepository() {

    when(playerCache.get(1L)).thenReturn(Optional.empty());
    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

    PlayerDto playerDto = playerService.getPlayerById(1L);

    assertNotNull(playerDto);
    assertEquals(player.getName(), playerDto.getName());
    verify(playerCache, times(1)).put(1L, player);
  }

  @Test
  void testGetPlayerByIdThrowsExceptionWhenPlayerNotFound() {
    when(playerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> playerService.getPlayerById(1L));
  }

  @Test
  void testGetPlayerByIdFromCache() {

        when(playerCache.get(1L)).thenReturn(Optional.of(player));

        PlayerDto playerDto = playerService.getPlayerById(1L);

        assertNotNull(playerDto);
        assertEquals(player.getName(), playerDto.getName());
        verify(playerRepository, never()).findById(1L);
  }

  @Test
  void testCreatePlayer() {
    PlayerDto playerDto = new PlayerDto();
    playerDto.setName("player1");
    playerDto.setUsername("username1");
    playerDto.setCountryId(1L);
    playerDto.setChampionshipIds(Set.of(1L));

    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
    player.setChampionships(List.of(championship));
    when(playerRepository.save(any(Player.class))).thenReturn(player);

    PlayerDto result = playerService.createPlayer(playerDto);

    assertNotNull(result);
    assertEquals(playerDto.getName(), result.getName());
    assertEquals(playerDto.getUsername(), result.getUsername());
  }

  @Test
  void testCreatePlayerThrowsException() {
    PlayerDto playerDto = new PlayerDto();
    playerDto.setName("player1");
    playerDto.setUsername("username1");
    playerDto.setCountryId(1L);
    playerDto.setChampionshipIds(Set.of(1L));

    when(countryRepository.findById(1L)).thenThrow(new ResourceNotFoundException("Country not found"));

    assertThrows(ResourceNotFoundException.class, () -> playerService.createPlayer(playerDto));
  }

  @Test
  void testCreatePlayerThrowsExceptionWhenNameIsEmpty() {
    PlayerDto playerDto = new PlayerDto();
    playerDto.setName("");
    playerDto.setUsername("username1");
    playerDto.setCountryId(1L);
    playerDto.setChampionshipIds(Set.of(1L));

    assertThrows(BadRequestException.class, () -> playerService.createPlayer(playerDto));
  }

  @Test
  void testCreatePlayerThrowsExceptionWhenUsernameIsEmpty() {
    PlayerDto playerDto = new PlayerDto();
    playerDto.setName("player1");
    playerDto.setUsername("");
    playerDto.setCountryId(1L);
    playerDto.setChampionshipIds(Set.of(1L));

    assertThrows(BadRequestException.class, () -> playerService.createPlayer(playerDto));
  }

  @Test
  void testCreatePlayers() {
    PlayerDto playerDto1 = new PlayerDto();
    playerDto1.setName("player1");
    playerDto1.setUsername("username1");
    playerDto1.setCountryId(1L);
    playerDto1.setChampionshipIds(Set.of());

    PlayerDto playerDto2 = new PlayerDto();
    playerDto2.setName("player2");
    playerDto2.setUsername("username2");
    playerDto2.setCountryId(1L);
    playerDto2.setChampionshipIds(Set.of());

    List<PlayerDto> playerDtos = List.of(playerDto1, playerDto2);

    Player player1 = new Player();
    player1.setName("player1");
    player1.setUsername("username1");
    player1.setCountry(country);
    player1.setChampionships(List.of());

    Player player2 = new Player();
    player2.setName("player2");
    player2.setUsername("username2");
    player2.setCountry(country);
    player2.setChampionships(List.of());

    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
    when(playerRepository.save(player1)).thenReturn(player1);
    when(playerRepository.save(player2)).thenReturn(player2);

    List<PlayerDto> result = playerService.createPlayers(playerDtos);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(playerDto1.getName(), result.get(0).getName());
    assertEquals(playerDto1.getUsername(), result.get(0).getUsername());
    assertEquals(playerDto2.getName(), result.get(1).getName());
    assertEquals(playerDto2.getUsername(), result.get(1).getUsername());
  }

  @Test
  void testCreatePlayersThrowsExceptionWhenNameIsEmpty() {
    PlayerDto playerDto1 = new PlayerDto();
    playerDto1.setName("");
    playerDto1.setUsername("username1");
    playerDto1.setCountryId(1L);
    playerDto1.setChampionshipIds(Set.of(1L));

    PlayerDto playerDto2 = new PlayerDto();
    playerDto2.setName("player2");
    playerDto2.setUsername("username2");
    playerDto2.setCountryId(1L);
    playerDto2.setChampionshipIds(Set.of(1L));

    List<PlayerDto> playerDtos = List.of(playerDto1, playerDto2);

    assertThrows(BadRequestException.class, () -> playerService.createPlayers(playerDtos));
  }

  @Test
  void testCreatePlayersThrowsExceptionWhenUsernameIsEmpty() {
    PlayerDto playerDto1 = new PlayerDto();
    playerDto1.setName("player1");
    playerDto1.setUsername("");
    playerDto1.setCountryId(1L);
    playerDto1.setChampionshipIds(Set.of(1L));

    PlayerDto playerDto2 = new PlayerDto();
    playerDto2.setName("player2");
    playerDto2.setUsername("username2");
    playerDto2.setCountryId(1L);
    playerDto2.setChampionshipIds(Set.of(1L));

    List<PlayerDto> playerDtos = List.of(playerDto1, playerDto2);

    assertThrows(BadRequestException.class, () -> playerService.createPlayers(playerDtos));
  }

  @Test
  void testGetAllPlayers() {
    List<Player> players = List.of(player);

    PlayerDto expectedPlayerDto = new PlayerDto();
    expectedPlayerDto.setName("player1");
    expectedPlayerDto.setUsername("username1");
    expectedPlayerDto.setCountryId(1L);
    expectedPlayerDto.setChampionshipIds(Set.of(1L));


    when(playerRepository.findAll()).thenReturn(players);

    List<PlayerDto> result = playerService.getAllPlayers();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(expectedPlayerDto, result.get(0));
  }

  @Test
  void testUpdatePlayer() {
    PlayerDto updatedPlayerDto = new PlayerDto();
    updatedPlayerDto.setName("updatedPlayer");
    updatedPlayerDto.setUsername("updatedUsername");
    updatedPlayerDto.setCountryId(1L);
    updatedPlayerDto.setChampionshipIds(Set.of(1L));

    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
    when(playerRepository.save(any(Player.class))).thenReturn(player);

    PlayerDto result = playerService.updatePlayer(1L, updatedPlayerDto);

    assertNotNull(result);
    assertEquals(updatedPlayerDto.getName(), result.getName());
    assertEquals(updatedPlayerDto.getUsername(), result.getUsername());
  }

  @Test
  void testUpdatePlayerWithNullChampionships() {
    PlayerDto updatedPlayerDto = new PlayerDto();
    updatedPlayerDto.setName("updatedPlayer");
    updatedPlayerDto.setUsername("updatedUsername");
    updatedPlayerDto.setCountryId(1L);
    updatedPlayerDto.setChampionshipIds(null);

    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
    when(playerRepository.save(any(Player.class))).thenReturn(player);

    PlayerDto result = playerService.updatePlayer(1L, updatedPlayerDto);

    assertNotNull(result);
    assertEquals(updatedPlayerDto.getName(), result.getName());
    assertEquals(updatedPlayerDto.getUsername(), result.getUsername());
    assertTrue(result.getChampionshipIds().isEmpty());
  }

  @Test
  void testUpdatePlayerThrowsExceptionWhenPlayerNotFound() {
    PlayerDto updatedPlayerDto = new PlayerDto();
    updatedPlayerDto.setName("updatedPlayer");
    updatedPlayerDto.setUsername("updatedUsername");
    updatedPlayerDto.setCountryId(1L);
    updatedPlayerDto.setChampionshipIds(Set.of(1L));

    when(playerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> playerService.updatePlayer(1L, updatedPlayerDto));
  }

  @Test
  void testUpdatePlayerThrowsExceptionWhenCountryNotFound() {
    PlayerDto updatedPlayerDto = new PlayerDto();
    updatedPlayerDto.setName("updatedPlayer");
    updatedPlayerDto.setUsername("updatedUsername");
    updatedPlayerDto.setCountryId(1L);
    updatedPlayerDto.setChampionshipIds(Set.of(1L));

    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
    when(countryRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> playerService.updatePlayer(1L, updatedPlayerDto));
  }

  @Test
  void testUpdatePlayerThrowsExceptionWhenNameIsEmpty() {
    PlayerDto updatedPlayerDto = new PlayerDto();
    updatedPlayerDto.setName("");
    updatedPlayerDto.setUsername("updatedUsername");
    updatedPlayerDto.setCountryId(1L);
    updatedPlayerDto.setChampionshipIds(Set.of(1L));

    assertThrows(BadRequestException.class, () -> playerService.updatePlayer(1L, updatedPlayerDto));
  }

  @Test
  void testUpdatePlayerThrowsExceptionWhenUsernameIsEmpty() {
    PlayerDto updatedPlayerDto = new PlayerDto();
    updatedPlayerDto.setName("updatedPlayer");
    updatedPlayerDto.setUsername("");
    updatedPlayerDto.setCountryId(1L);
    updatedPlayerDto.setChampionshipIds(Set.of(1L));

    assertThrows(BadRequestException.class, () -> playerService.updatePlayer(1L, updatedPlayerDto));
  }

  @Test
  void testDeletePlayer() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

    playerService.deletePlayer(1L);

    verify(playerRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDeletePlayerThrowsExceptionWhenPlayerNotFound() {
    when(playerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> playerService.deletePlayer(1L));
  }

}
