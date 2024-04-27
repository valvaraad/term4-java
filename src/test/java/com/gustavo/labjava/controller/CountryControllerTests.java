package com.gustavo.labjava.controller;

import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.service.CounterService;
import com.gustavo.labjava.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CountryControllerTests {

  @InjectMocks
  private CountryController countryController;

  @Mock
  private CountryService countryService;

  @Mock
  private CounterService requestCounter;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateCountry() {
    CountryDto countryDto = new CountryDto();
    when(countryService.createCountry(countryDto)).thenReturn(countryDto);
    doNothing().when(requestCounter).increment();

    ResponseEntity<CountryDto> response = countryController.createCountry(countryDto);

    assertEquals(countryDto, response.getBody());
  }

  @Test
  void testCreateCountries() {
    List<CountryDto> countryDtos = Arrays.asList(new CountryDto(), new CountryDto());
    when(countryService.createCountries(countryDtos)).thenReturn(countryDtos);
    doNothing().when(requestCounter).increment();

    ResponseEntity<List<CountryDto>> response = countryController.createPlayers(countryDtos);

    assertEquals(countryDtos, response.getBody());
  }

  @Test
  void testGetCountryById() {
    CountryDto countryDto = new CountryDto();
    when(countryService.getCountryById(1L)).thenReturn(countryDto);
    doNothing().when(requestCounter).increment();

    ResponseEntity<CountryDto> response = countryController.getCountryById(1L);

    assertEquals(countryDto, response.getBody());
  }

  @Test
  void testGetAllCountries() {
    List<CountryDto> countryDtos = Arrays.asList(new CountryDto(), new CountryDto());
    when(countryService.getAllCountries()).thenReturn(countryDtos);
    doNothing().when(requestCounter).increment();

    ResponseEntity<List<CountryDto>> response = countryController.getAllCountries();

    assertEquals(countryDtos, response.getBody());
  }

  @Test
  void testUpdateCountry() {
    CountryDto updatedCountryDto = new CountryDto();
    when(countryService.updateCountry(1L, updatedCountryDto)).thenReturn(updatedCountryDto);
    doNothing().when(requestCounter).increment();

    ResponseEntity<CountryDto> response = countryController.updateCountry(1L, updatedCountryDto);

    assertEquals(updatedCountryDto, response.getBody());
  }

  @Test
  void testDeleteCountry() {
    Long countryId = 1L;
    doNothing().when(countryService).deleteCountry(countryId);
    doNothing().when(requestCounter).increment();

    ResponseEntity<String> response = countryController.deleteCountry(countryId);

    assertEquals("Country deleted.", response.getBody());
  }
}