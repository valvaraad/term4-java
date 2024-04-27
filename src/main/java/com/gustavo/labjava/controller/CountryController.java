package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.service.CounterService;
import com.gustavo.labjava.service.CountryService;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
public class CountryController {

  private final CountryService countryService;
  private final CounterService requestCounter;

  public CountryController(CountryService countryService, CounterService requestCounter) {
    this.countryService = countryService;
    this.requestCounter = requestCounter;
  }

  @PostMapping("/create")
  public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
    requestCounter.increment();
    CountryDto savedCountry = countryService.createCountry(countryDto);
    return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
  }

  @PostMapping("/createbulk")
  public ResponseEntity<List<CountryDto>> createPlayers(@RequestBody List<CountryDto> countryDtos) {
    requestCounter.increment();
    List<CountryDto> savedCountries = countryService.createCountries(countryDtos);
    return new ResponseEntity<>(savedCountries, HttpStatus.CREATED);
  }

  @GetMapping("{id}")
  public ResponseEntity<CountryDto> getCountryById(@PathVariable("id") Long countryId) {
    requestCounter.increment();
    CountryDto countryDto = countryService.getCountryById(countryId);
    return ResponseEntity.ok(countryDto);
  }


  @GetMapping
  public ResponseEntity<List<CountryDto>> getAllCountries() {
    requestCounter.increment();
    List<CountryDto> countries = countryService.getAllCountries();
    return ResponseEntity.ok(countries);
  }

  @PutMapping("{id}")
  public ResponseEntity<CountryDto> updateCountry(@PathVariable("id") Long countryId,
                                                  @RequestBody CountryDto updatedCountry) {
    requestCounter.increment();
    CountryDto countryDto = countryService.updateCountry(countryId, updatedCountry);
    return ResponseEntity.ok(countryDto);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deleteCountry(@PathVariable("id") Long countryId) {
    requestCounter.increment();
    countryService.deleteCountry(countryId);
    return ResponseEntity.ok("Country deleted.");
  }
}
