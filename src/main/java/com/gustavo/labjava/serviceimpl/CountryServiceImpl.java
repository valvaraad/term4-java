package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.aspect.Logger;
import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.exception.*;
import com.gustavo.labjava.mapper.CountryMapper;
import com.gustavo.labjava.model.Country;
import com.gustavo.labjava.repository.CountryRepository;
import com.gustavo.labjava.service.CountryService;
import com.gustavo.labjava.utils.cache.GenericCache;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

  private final CountryRepository countryRepository;
  private final CountryMapper countryMapper;
  private final GenericCache<Long, Country> countryCache;
  String wrongParameters = "Wrong country parameters";

  @Autowired
  public CountryServiceImpl(CountryRepository countryRepository,
                            GenericCache<Long, Country> countryCache) {
    this.countryRepository = countryRepository;
    this.countryMapper = new CountryMapper();
    this.countryCache = countryCache;
  }

  @Override
  @Logger
  public CountryDto createCountry(CountryDto countryDto) {

    if (countryDto.getCode().isEmpty() || countryDto.getName().isEmpty()) {
      throw new BadRequestException(wrongParameters);
    }

    Country country = countryMapper.mapToCountry(countryDto);
    Country savedCountry = countryRepository.save(country);
    return countryMapper.mapToCountryDto(savedCountry);
  }

  @Override
  @Logger
  public List<CountryDto> createCountries(List<CountryDto> countryDtos) {
    if (countryDtos.stream().anyMatch(c -> (c.getName().isEmpty() || c.getCode().isEmpty()))) {
      throw new BadRequestException(wrongParameters);
    }

    return countryDtos.stream()
        .map(c -> countryRepository.save(countryMapper.mapToCountry(c)))
        .map(countryMapper::mapToCountryDto).toList();
  }

  @Override
  @Logger
  public CountryDto getCountryById(Long countryId) {
    Country country = countryCache.get(countryId)
        .orElseGet(() -> countryRepository.findById(countryId).orElseThrow(() ->
            new ResourceNotFoundException("Country with ID " + countryId + " does not exist.")));
    return countryMapper.mapToCountryDto(country);
  }

  @Override
  @Logger
  public List<CountryDto> getAllCountries() {
    List<Country> countries = countryRepository.findAll();
    return countries.stream().map(countryMapper::mapToCountryDto).toList();
  }

  @Override
  @Logger
  public CountryDto updateCountry(Long countryId, CountryDto updateCountry) {

    if (updateCountry.getName().isEmpty() || updateCountry.getCode().isEmpty()) {
      throw new BadRequestException(wrongParameters);
    }

    Country country = countryCache.get(countryId)
        .orElseGet(() -> countryRepository.findById(countryId).orElseThrow(() ->
            new ResourceNotFoundException("Country with ID " + countryId + " does not exist.")));

    country.setName(updateCountry.getName());
    country.setCode(updateCountry.getCode());

    Country updatedCountryObj = countryRepository.save(country);
    countryCache.remove(countryId);
    return countryMapper.mapToCountryDto(updatedCountryObj);
  }

  @Override
  @Logger
  public void deleteCountry(Long countryId) {

    if (countryRepository.findById(countryId).isEmpty()) {
      throw new ResourceNotFoundException("There is no country with given ID: " + countryId);
    }

    countryCache.remove(countryId);
    countryRepository.deleteById(countryId);
  }

}
