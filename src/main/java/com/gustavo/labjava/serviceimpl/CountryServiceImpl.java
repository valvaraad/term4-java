package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.dto.CountryDto;
import com.gustavo.labjava.exception.ResourceNotFoundException;
import com.gustavo.labjava.mapper.CountryMapper;
import com.gustavo.labjava.model.Country;
import com.gustavo.labjava.repository.CountryRepository;
import com.gustavo.labjava.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository)  {
        this.countryRepository = countryRepository;
        this.countryMapper = new CountryMapper();
    }
    @Override
    public CountryDto createCountry(CountryDto countryDto) {
        Country country = countryMapper.mapToCountry(countryDto);
        Country savedCountry = countryRepository.save(country);
        return countryMapper.mapToCountryDto(savedCountry);
    }

    @Override
    public CountryDto getCountryById(Long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(() ->
                new ResourceNotFoundException("Country with ID " + countryId + " does not exist."));
        return countryMapper.mapToCountryDto(country);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream().map(countryMapper::mapToCountryDto).toList();
    }

    @Override
    public CountryDto updateCountry(Long countryId, CountryDto updateCountry) {

        Country country = countryRepository.findById(countryId).orElseThrow(
                () -> new ResourceNotFoundException("There is no country with given ID: " + countryId));

        country.setName(updateCountry.getName());
        country.setCode(updateCountry.getCode());

        Country updatedCountryObj = countryRepository.save(country);
        return countryMapper.mapToCountryDto(updatedCountryObj);
    }

    @Override
    public void deleteCountry(Long countryId) {

        countryRepository.findById(countryId).orElseThrow(
                () -> new ResourceNotFoundException("There is no country with given ID: " + countryId));

        countryRepository.deleteById(countryId);
    }
}
