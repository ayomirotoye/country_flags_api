package com.demo.country_flag_api.controllers;

import com.demo.country_flag_api.business_logic.ICountryBusinessLogic;
import com.demo.country_flag_api.models.Country;
import com.demo.country_flag_api.models.CountryRequestDto;
import com.demo.country_flag_api.models.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("countries")
public class CountryController {

    private final ICountryBusinessLogic countryBusinessLogic;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Country>> getCountries(
            @RequestParam(defaultValue = "") String countryIsoCode,
            @RequestParam(defaultValue = "") String countryName,
            @RequestParam(defaultValue = "") String cursor
    ) {
        return StringUtils.isAllBlank(countryIsoCode, countryName) ?
                ResponseEntity.ok(countryBusinessLogic.getCountriesAndFlags(CountryRequestDto.builder().build(), cursor)) :
                ResponseEntity.ok(countryBusinessLogic.getCountries(CountryRequestDto.builder()
                        .countryName(countryName)
                        .countryIsoCode(countryIsoCode)
                        .build(), cursor));
    }

    @GetMapping("search")
    public ResponseEntity<ResponseWrapper<Country>> searchCountries(
            @RequestParam String searchText,
            @RequestParam(defaultValue = "") String cursor
    ) {
        return ResponseEntity.ok(countryBusinessLogic.searchCountries(searchText, cursor));
    }
}
