package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.service.ChampionshipService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/championships")
public class ChampionshipController {

  private final ChampionshipService championshipService;

  public ChampionshipController(ChampionshipService championshipService) {
    this.championshipService = championshipService;
  }

  @PostMapping("/create")
  public ResponseEntity<ChampionshipDto> createChampionship(
      @RequestBody ChampionshipDto championshipDto) {
    ChampionshipDto savedChampionship = championshipService.createChampionship(championshipDto);
    return new ResponseEntity<>(savedChampionship, HttpStatus.CREATED);
  }

  @PostMapping("/createbulk")
  public ResponseEntity<List<ChampionshipDto>> createChampionships(
      @RequestBody List<ChampionshipDto> championshipDtos) {
    List<ChampionshipDto> savedChampionships = championshipService
        .createChampionships(championshipDtos);
    return new ResponseEntity<>(savedChampionships, HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public ResponseEntity<ChampionshipDto> getChampionshipById(
      @PathVariable("id") Long championshipId) {
    ChampionshipDto championshipDto = championshipService.getChampionshipById(championshipId);
    return ResponseEntity.ok(championshipDto);
  }


  @GetMapping
  public ResponseEntity<List<ChampionshipDto>> getAllChampionships() {
    List<ChampionshipDto> championships = championshipService.getAllChampionships();
    return ResponseEntity.ok(championships);
  }

  @GetMapping("/year/{year}")
  public ResponseEntity<ChampionshipDto> getChampionshipByYear(@PathVariable("year") Integer year) {
    ChampionshipDto championshipDto = championshipService.getChampionshipByYear(year);
    return ResponseEntity.ok(championshipDto);
  }

  @PutMapping("{id}")
  public ResponseEntity<ChampionshipDto> updateChampionship(@PathVariable("id") Long championshipId,
                                                            @RequestBody
                                                            ChampionshipDto updatedChampionship) {
    ChampionshipDto championshipDto =
        championshipService.updateChampionship(championshipId, updatedChampionship);
    return ResponseEntity.ok(championshipDto);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deleteChampionship(@PathVariable("id") Long championshipId) {
    championshipService.deleteChampionship(championshipId);
    return ResponseEntity.ok("Championship deleted.");
  }
}
