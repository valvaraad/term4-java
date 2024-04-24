package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.exception.BadRequestException;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.model.Championship;
import com.gustavo.labjava.model.Player;
import com.gustavo.labjava.repository.ChampionshipRepository;
import com.gustavo.labjava.repository.PlayerRepository;
import com.gustavo.labjava.serviceimpl.ChampionshipServiceImpl;
import com.gustavo.labjava.utils.cache.GenericCache;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChampionshipServiceTests {

  @Mock
  private ChampionshipRepository championshipRepository;

  @Mock
  private PlayerRepository playerRepository;

  @Mock
  private GenericCache<Long, Championship> championshipCache;

  @InjectMocks
  private ChampionshipServiceImpl championshipService;

  private Championship championship;

  @BeforeEach
  public void setUp() {
    championship = new Championship();
    championship.setYear(2000);
    championship.setPlace("place1");
    championship.setId(1L);
  }

  @Test
  void testGetChampionshipByIdFromRepository() {
    when(championshipCache.get(1L)).thenReturn(Optional.empty());
    when(championshipRepository.findById(1L)).thenReturn(Optional.of(championship));

    ChampionshipDto championshipDto = championshipService.getChampionshipById(1L);

    assertNotNull(championshipDto);
    assertEquals(championship.getYear(), championshipDto.getYear());
    verify(championshipCache, times(1)).put(1L, championship);
  }

  @Test
  void testGetChampionshipByIdThrowsExceptionWhenChampionshipNotFound() {
    when(championshipRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> championshipService.getChampionshipById(1L));
  }

  @Test
  void testGetAllChampionships() {

    Championship championship1 = new Championship();
    championship1.setId(1L);
    championship1.setYear(2000);
    championship1.setPlace("place1");

    Championship championship2 = new Championship();
    championship2.setId(2L);
    championship2.setYear(2001);
    championship2.setPlace("place2");

    List<Championship> championships = List.of(championship1, championship2);

    when(championshipRepository.findAll()).thenReturn(championships);

    List<ChampionshipDto> result = championshipService.getAllChampionships();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(championship1.getYear(), result.get(0).getYear());
    assertEquals(championship1.getPlace(), result.get(0).getPlace());
    assertEquals(championship2.getYear(), result.get(1).getYear());
    assertEquals(championship2.getPlace(), result.get(1).getPlace());
  }

  @Test
  void testGetChampionshipByYear() {

    Championship championship = new Championship();
    championship.setId(1L);
    championship.setYear(2000);
    championship.setPlace("place1");

    when(championshipRepository.findByYear(2000)).thenReturn(Optional.of(championship));

    ChampionshipDto result = championshipService.getChampionshipByYear(2000);

    assertNotNull(result);
    assertEquals(championship.getYear(), result.getYear());
    assertEquals(championship.getPlace(), result.getPlace());
  }

  @Test
  void testGetChampionshipByYearThrowsExceptionWhenChampionshipNotFound() {
    int year = 2000;
    when(championshipRepository.findByYear(year)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> championshipService.getChampionshipByYear(year));
  }

  @Test
  void testCreateChampionship() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    championshipDto.setYear(2000);
    championshipDto.setPlace("place1");

    when(championshipRepository.save(any(Championship.class))).thenReturn(championship);

    ChampionshipDto result = championshipService.createChampionship(championshipDto);

    assertNotNull(result);
    assertEquals(championshipDto.getYear(), result.getYear());
    assertEquals(championshipDto.getPlace(), result.getPlace());
  }

  @Test
  void testCreateChampionshipThrowsExceptionWhenYearIsLessThan1800() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    championshipDto.setYear(1700);
    championshipDto.setPlace("place1");

    assertThrows(BadRequestException.class, () -> championshipService.createChampionship(championshipDto));
  }

  @Test
  void testCreateChampionshipThrowsExceptionWhenPlaceIsEmpty() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    championshipDto.setYear(2000);
    championshipDto.setPlace("");

    assertThrows(BadRequestException.class, () -> championshipService.createChampionship(championshipDto));
  }

  @Test
  void testCreateChampionships() {

    ChampionshipDto championshipDto1 = new ChampionshipDto();
    championshipDto1.setYear(2000);
    championshipDto1.setPlace("place1");

    ChampionshipDto championshipDto2 = new ChampionshipDto();
    championshipDto2.setYear(2001);
    championshipDto2.setPlace("place2");

    List<ChampionshipDto> championshipDtos = List.of(championshipDto1, championshipDto2);

    Championship championship1 = new Championship();
    championship1.setId(1L);
    championship1.setYear(2000);
    championship1.setPlace("place1");

    Championship championship2 = new Championship();
    championship2.setId(2L);
    championship2.setYear(2001);
    championship2.setPlace("place2");

    when(championshipRepository.save(any(Championship.class))).thenReturn(championship1, championship2);

    List<ChampionshipDto> result = championshipService.createChampionships(championshipDtos);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(championshipDto1.getYear(), result.get(0).getYear());
    assertEquals(championshipDto1.getPlace(), result.get(0).getPlace());
    assertEquals(championshipDto2.getYear(), result.get(1).getYear());
    assertEquals(championshipDto2.getPlace(), result.get(1).getPlace());
  }

  @Test
  void testCreateChampionshipsThrowsExceptionWhenPlaceIsEmpty() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    championshipDto.setYear(2000);
    championshipDto.setPlace("");

    List<ChampionshipDto> championshipDtos = List.of(championshipDto);

    assertThrows(BadRequestException.class, () -> championshipService.createChampionships(championshipDtos));
  }

  @Test
  void testCreateChampionshipsThrowsExceptionWhenYearIsLessThan1800() {
    ChampionshipDto championshipDto = new ChampionshipDto();
    championshipDto.setYear(1700);
    championshipDto.setPlace("place1");

    List<ChampionshipDto> championshipDtos = List.of(championshipDto);

    assertThrows(BadRequestException.class, () -> championshipService.createChampionships(championshipDtos));
  }

  @Test
  void testUpdateChampionship() {
    ChampionshipDto updatedChampionshipDto = new ChampionshipDto();
    updatedChampionshipDto.setYear(2000);
    updatedChampionshipDto.setPlace("place1");

    Championship existingChampionship = new Championship();
    existingChampionship.setId(1L);
    existingChampionship.setYear(2000);
    existingChampionship.setPlace("place1");

    when(championshipRepository.findById(1L)).thenReturn(Optional.of(existingChampionship));
    when(championshipRepository.save(any(Championship.class))).thenReturn(existingChampionship);

    ChampionshipDto result = championshipService.updateChampionship(1L, updatedChampionshipDto);

    assertNotNull(result);
    assertEquals(updatedChampionshipDto.getYear(), result.getYear());
    assertEquals(updatedChampionshipDto.getPlace(), result.getPlace());
  }

  @Test
  void testUpdateChampionshipThrowsExceptionWhenChampionshipNotFound() {
    ChampionshipDto updatedChampionshipDto = new ChampionshipDto();
    updatedChampionshipDto.setYear(2001);
    updatedChampionshipDto.setPlace("place2");

    when(championshipRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> championshipService.updateChampionship(1L, updatedChampionshipDto));
  }

  @Test
  void testUpdateChampionshipThrowsExceptionWhenPlaceIsEmpty() {
    ChampionshipDto updatedChampionshipDto = new ChampionshipDto();
    updatedChampionshipDto.setYear(2000);
    updatedChampionshipDto.setPlace("");

    assertThrows(BadRequestException.class, () -> championshipService.updateChampionship(1L, updatedChampionshipDto));
  }

  @Test
  void testUpdateChampionshipThrowsExceptionWhenYearIsLessThan1800() {
    ChampionshipDto updatedChampionshipDto = new ChampionshipDto();
    updatedChampionshipDto.setYear(1700);
    updatedChampionshipDto.setPlace("place1");

    assertThrows(BadRequestException.class, () -> championshipService.updateChampionship(1L, updatedChampionshipDto));
  }

  @Test
  void testDeleteChampionship() {
    when(championshipRepository.findById(1L)).thenReturn(Optional.of(championship));

    championshipService.deleteChampionship(1L);

    verify(championshipRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteChampionshipThrowsExceptionWhenChampionshipNotFound() {
    when(championshipRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> championshipService.deleteChampionship(1L));
  }

  @Test
  void testDeleteChampionshipWhenPlayersAreNotNull() {

    Player player = new Player();
    player.setId(1L);
    player.setName("Player1");
    player.setUsername("Username1");
    player.setChampionships(new ArrayList<>()); // Initialize championships field

    Championship championshipWithPlayers = new Championship();
    championshipWithPlayers.setId(1L);
    championshipWithPlayers.setYear(2000);
    championshipWithPlayers.setPlace("place1");
    championshipWithPlayers.setPlayers(new ArrayList<>(List.of(player))); // Create a modifiable list

    when(championshipRepository.findById(1L)).thenReturn(Optional.of(championshipWithPlayers));
    when(playerRepository.saveAll(anyList())).thenReturn(championshipWithPlayers.getPlayers()); // Set up the playerRepository.saveAll mock

    championshipService.deleteChampionship(1L);

    verify(championshipRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteChampionshipDeletesPlayerWhenPlayerHasOneChampionship() {
    Player player = new Player();
    player.setId(1L);
    player.setName("Player1");
    player.setUsername("Username1");
    player.setChampionships(new ArrayList<>()); // Initialize championships field

    Championship championshipWithOnePlayer = new Championship();
    championshipWithOnePlayer.setId(1L);
    championshipWithOnePlayer.setYear(2000);
    championshipWithOnePlayer.setPlace("place1");
    championshipWithOnePlayer.setPlayers(new ArrayList<>(List.of(player))); // Create a modifiable list

    player.getChampionships().add(championshipWithOnePlayer); // Add the championship to the player's championships

    when(championshipRepository.findById(1L)).thenReturn(Optional.of(championshipWithOnePlayer));
    when(playerRepository.saveAll(anyList())).thenReturn(championshipWithOnePlayer.getPlayers()); // Set up the playerRepository.saveAll mock

    championshipService.deleteChampionship(1L);

    verify(championshipRepository, times(1)).deleteById(1L);
    verify(playerRepository, times(1)).deleteAll(List.of(player));
  }
}