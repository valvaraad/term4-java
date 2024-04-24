package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.exception.BadRequestException;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.model.Country;
import com.gustavo.labjava.repository.CountryRepository;
import com.gustavo.labjava.serviceimpl.CountryServiceImpl;
import com.gustavo.labjava.utils.cache.GenericCache;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTests {

  @Mock
  private CountryRepository countryRepository;

  @Mock
  private GenericCache<Long, Country> countryCache;

  @InjectMocks
  private CountryServiceImpl countryService;

  private Country country;

  @BeforeEach
  public void setUp() {
    country = new Country();
    country.setName("country1");
    country.setCode("C1");
    country.setId(1L);
  }

  @Test
  void testCreateCountry() {
    CountryDto countryDto = new CountryDto();
    countryDto.setName("country1");
    countryDto.setCode("C1");

    when(countryRepository.save(any(Country.class))).thenReturn(country);

    CountryDto result = countryService.createCountry(countryDto);

    assertNotNull(result);
    assertEquals(countryDto.getName(), result.getName());
    assertEquals(countryDto.getCode(), result.getCode());
  }

  @Test
  void testCreateCountryThrowsExceptionWhenNameIsEmpty() {
    CountryDto countryDto = new CountryDto();
    countryDto.setName("");
    countryDto.setCode("C1");

    assertThrows(BadRequestException.class, () -> countryService.createCountry(countryDto));
  }

  @Test
  void testCreateCountryThrowsExceptionWhenCodeIsEmpty() {
    CountryDto countryDto = new CountryDto();
    countryDto.setName("country1");
    countryDto.setCode("");

    assertThrows(BadRequestException.class, () -> countryService.createCountry(countryDto));
  }

  @Test
  void testCreateCountries() {
    CountryDto countryDto1 = new CountryDto();
    countryDto1.setName("country1");
    countryDto1.setCode("C1");

    CountryDto countryDto2 = new CountryDto();
    countryDto2.setName("country2");
    countryDto2.setCode("C2");

    List<CountryDto> countryDtos = List.of(countryDto1, countryDto2);

    Country country1 = new Country();
    country1.setName("country1");
    country1.setCode("C1");

    Country country2 = new Country();
    country2.setName("country2");
    country2.setCode("C2");

    when(countryRepository.save(any(Country.class))).thenReturn(country1, country2);

    List<CountryDto> result = countryService.createCountries(countryDtos);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(countryDto1.getName(), result.get(0).getName());
    assertEquals(countryDto1.getCode(), result.get(0).getCode());
    assertEquals(countryDto2.getName(), result.get(1).getName());
    assertEquals(countryDto2.getCode(), result.get(1).getCode());
  }

  @Test
  void testCreateCountriesThrowsExceptionWhenNameIsEmpty() {
    CountryDto countryDto1 = new CountryDto();
    countryDto1.setName("");
    countryDto1.setCode("C1");

    CountryDto countryDto2 = new CountryDto();
    countryDto2.setName("country2");
    countryDto2.setCode("C2");

    List<CountryDto> countryDtos = List.of(countryDto1, countryDto2);

    assertThrows(BadRequestException.class, () -> countryService.createCountries(countryDtos));
  }

  @Test
  void testCreateCountriesThrowsExceptionWhenCodeIsEmpty() {
    CountryDto countryDto1 = new CountryDto();
    countryDto1.setName("country1");
    countryDto1.setCode("C1");

    CountryDto countryDto2 = new CountryDto();
    countryDto2.setName("country2");
    countryDto2.setCode("");

    List<CountryDto> countryDtos = List.of(countryDto1, countryDto2);

    assertThrows(BadRequestException.class, () -> countryService.createCountries(countryDtos));
  }

  @Test
  void testGetCountryById() {
    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

    CountryDto result = countryService.getCountryById(1L);

    assertNotNull(result);
    assertEquals(country.getName(), result.getName());
    assertEquals(country.getCode(), result.getCode());
  }

  @Test
  void testGetCountryByIdThrowsExceptionWhenCountryNotFound() {
    when(countryRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> countryService.getCountryById(1L));
  }

  @Test
  void testGetAllCountries() {
    Country country2 = new Country();
    country2.setName("country2");
    country2.setCode("C2");
    country2.setId(2L);

    List<Country> countries = List.of(country, country2);

    when(countryRepository.findAll()).thenReturn(countries);

    List<CountryDto> result = countryService.getAllCountries();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(country.getName(), result.get(0).getName());
    assertEquals(country.getCode(), result.get(0).getCode());
    assertEquals(country2.getName(), result.get(1).getName());
    assertEquals(country2.getCode(), result.get(1).getCode());
  }

  @Test
  void testUpdateCountry() {
    CountryDto updatedCountryDto = new CountryDto();
    updatedCountryDto.setName("updatedCountry");
    updatedCountryDto.setCode("UC1");

    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
    when(countryRepository.save(any(Country.class))).thenReturn(country);

    CountryDto result = countryService.updateCountry(1L, updatedCountryDto);

    assertNotNull(result);
    assertEquals(updatedCountryDto.getName(), result.getName());
    assertEquals(updatedCountryDto.getCode(), result.getCode());
  }

  @Test
  void testUpdateCountryThrowsExceptionWhenNameIsEmpty() {
    CountryDto updatedCountryDto = new CountryDto();
    updatedCountryDto.setName("");
    updatedCountryDto.setCode("UC1");

    assertThrows(BadRequestException.class, () -> countryService.updateCountry(1L, updatedCountryDto));
  }

  @Test
  void testUpdateCountryThrowsExceptionWhenCodeIsEmpty() {
    CountryDto updatedCountryDto = new CountryDto();
    updatedCountryDto.setName("updatedCountry");
    updatedCountryDto.setCode("");

    assertThrows(BadRequestException.class, () -> countryService.updateCountry(1L, updatedCountryDto));
  }

  @Test
  void testUpdateCountryThrowsExceptionWhenCountryNotFound() {
    CountryDto updatedCountryDto = new CountryDto();
    updatedCountryDto.setName("updatedCountry");
    updatedCountryDto.setCode("UC1");

    when(countryRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> countryService.updateCountry(1L, updatedCountryDto));
  }

  @Test
  void testDeleteCountry() {
    when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

    countryService.deleteCountry(1L);

    verify(countryRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteCountryThrowsExceptionWhenCountryNotFound() {
    when(countryRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> countryService.deleteCountry(1L));
  }
}