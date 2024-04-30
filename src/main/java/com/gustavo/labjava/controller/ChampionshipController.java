package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.ChampionshipDto;
import com.gustavo.labjava.service.ChampionshipService;
import com.gustavo.labjava.service.CounterService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/championships")
public class ChampionshipController {

  private final ChampionshipService championshipService;
  private final CounterService requestCounter;

  //ChampionshipController:17 - on-purpose checkstyle violation
  public ChampionshipController(ChampionshipService championshipService, CounterService requestCounter) {
    this.championshipService = championshipService;
    this.requestCounter = requestCounter;
  }

  @PostMapping("/create")
  public ResponseEntity<ChampionshipDto> createChampionship(
      @RequestBody ChampionshipDto championshipDto) {
    requestCounter.increment();
    ChampionshipDto savedChampionship = championshipService.createChampionship(championshipDto);
    return new ResponseEntity<>(savedChampionship, HttpStatus.CREATED);
  }

  @PostMapping("/createbulk")
  public ResponseEntity<List<ChampionshipDto>> createChampionships(
      @RequestBody List<ChampionshipDto> championshipDtos) {
    requestCounter.increment();
    List<ChampionshipDto> savedChampionships = championshipService
        .createChampionships(championshipDtos);
    return new ResponseEntity<>(savedChampionships, HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public ResponseEntity<ChampionshipDto> getChampionshipById(
      @PathVariable("id") Long championshipId) {
    requestCounter.increment();
    ChampionshipDto championshipDto = championshipService.getChampionshipById(championshipId);
    return ResponseEntity.ok(championshipDto);
  }


  @GetMapping
  public ResponseEntity<List<ChampionshipDto>> getAllChampionships() {
    requestCounter.increment();
    List<ChampionshipDto> championships = championshipService.getAllChampionships();
    return ResponseEntity.ok(championships);
  }

  @GetMapping("/year/{year}")
  public ResponseEntity<ChampionshipDto> getChampionshipByYear(@PathVariable("year") Integer year) {
    requestCounter.increment();
    ChampionshipDto championshipDto = championshipService.getChampionshipByYear(year);
    return ResponseEntity.ok(championshipDto);
  }

  @PutMapping("{id}")
  public ResponseEntity<ChampionshipDto> updateChampionship(@PathVariable("id") Long championshipId,
                                                            @RequestBody
                                                            ChampionshipDto updatedChampionship) {
    requestCounter.increment();
    ChampionshipDto championshipDto =
        championshipService.updateChampionship(championshipId, updatedChampionship);
    return ResponseEntity.ok(championshipDto);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deleteChampionship(@PathVariable("id") Long championshipId) {
    requestCounter.increment();
    championshipService.deleteChampionship(championshipId);
    return ResponseEntity.ok("Championship deleted.");
  }
}
