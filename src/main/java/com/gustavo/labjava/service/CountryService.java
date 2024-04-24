package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.dto.PlayerDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CountryService {

  CountryDto createCountry(CountryDto countryDto);

  List<CountryDto> createCountries(List<CountryDto> countryDtos);

  CountryDto getCountryById(Long countryId);

  List<CountryDto> getAllCountries();

  CountryDto updateCountry(Long countryId, CountryDto updateCountry);

  void deleteCountry(Long countryId);
}
