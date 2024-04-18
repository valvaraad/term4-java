package com.gustavo.labjava.service;

import com.gustavo.labjava.dto.CountryDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CountryService {

  CountryDto createCountry(CountryDto countryDto);

  CountryDto getCountryById(Long countryId);

  List<CountryDto> getAllCountries();

  CountryDto updateCountry(Long countryId, CountryDto updateCountry);

  void deleteCountry(Long countryId);
}
