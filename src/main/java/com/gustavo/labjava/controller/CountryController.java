package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.service.CountryService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
        CountryDto savedCountry = countryService.createCountry(countryDto);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable("id") Long countryId) {
        CountryDto countryDto = countryService.getCountryById(countryId);
        return ResponseEntity.ok(countryDto);
    }


    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<CountryDto> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    @PutMapping("{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable("id") Long countryId,
                                                    @RequestBody CountryDto updatedCountry) {
        CountryDto countryDto = countryService.updateCountry(countryId, updatedCountry);
        return ResponseEntity.ok(countryDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Long countryId) {
        countryService.deleteCountry(countryId);
        return ResponseEntity.ok("Country deleted.");
    }
}
